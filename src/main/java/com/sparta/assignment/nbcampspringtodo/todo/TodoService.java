package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.common.Verifier;
import com.sparta.assignment.nbcampspringtodo.user.User;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

  private final Verifier verifier;
  private TodoRepository todoRepository;

  @Transactional
  public TodoDetailResponseDto createTodo(
      TodoRequestDto requestDto, String username
  ) {
    User user = verifier.verifyUser(username);

    Todo todo = new Todo(requestDto, user);

    return new TodoDetailResponseDto(todoRepository.save(todo));
  }

  public TodoDetailResponseDto getTodoDetail(
      Long todoId, String username
  ) {
    Todo todo = verifier.verifyTodoWithUser(todoId, username);

    return new TodoDetailResponseDto(todo);
  }

  public List<TodoListResponseDto> getTodosByUserId(
      Long userId, String username
  ) {
    User targetUser = verifier.verifyUser(userId);
    User currentUser = verifier.verifyUser(username);

    List<Todo> todos;
    if (Objects.equals(targetUser, currentUser)) {
      todos = todoRepository.findAllByUserIdOrderByLastModifiedDateDesc(currentUser.getId());
    } else {
      todos = todoRepository.findAllByUserIdAndHiddenIsFalseOrderByLastModifiedDateDesc(currentUser.getId());
    }

    return todos.stream().map(TodoListResponseDto::new).toList();
  }

  public List<TodoListResponseDto> getAllNotHiddenTodos(String username) {
    User user = verifier.verifyUser(username);
    List<Todo> todos = todoRepository.findAllByHiddenIsFalseOrUserIdOrderByLastModifiedDateDesc(user.getId());

    return todos.stream().map(TodoListResponseDto::new).toList();
  }

  public List<TodoListResponseDto> searchTodoByTitle(
      String search, String username
  ) {
    User user = verifier.verifyUser(username);
    List<Todo> todos = todoRepository.findTodoByTitle(search, user.getId());

    return todos.stream().map(TodoListResponseDto::new).toList();
  }

  @Transactional
  public TodoListResponseDto updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    Todo todo = verifier.verifyTodoWithUser(todoId, username);

    todo.update(requestDto);

    return new TodoListResponseDto(todo);
  }

  @Transactional
  public String deleteTodo(Long todoId, String username) {
    Todo todo = verifier.verifyTodoWithUser(todoId, username);

    todoRepository.deleteById(todo.getId());

    return "username: " + todo.getTitle();
  }

}
