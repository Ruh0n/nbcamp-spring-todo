package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

  private UserRepository userRepository;
  private TodoRepository todoRepository;


  @Transactional
  public ResponseEntity<TodoResponseDto> createTodo(TodoRequestDto requestDto, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    Todo newTodo = new Todo(requestDto, user);

    return ResponseEntity.ok(new TodoResponseDto(todoRepository.save(newTodo)));
  }

  public ResponseEntity<List<TodoResponseDto>> getTodosByUserId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    List<Todo> todos = todoRepository.findAllByUserId(user.getId());

    return ResponseEntity.ok(todos.stream().map(TodoResponseDto::new).toList());
  }

  public ResponseEntity<List<TodoResponseDto>> searchTodoByTitle(String search) {
    List<Todo> searchResult = todoRepository.findAllByTitleContainsAndHiddenIsFalse(search);

    return ResponseEntity.ok(searchResult.stream().map(TodoResponseDto::new).toList());
  }

  public ResponseEntity<List<TodoResponseDto>> getAllNotHiddenTodos(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    List<Todo> notHiddenTodos = todoRepository.findAllByHiddenIsFalse();
    List<Todo> usersHiddenTodos = todoRepository.findAllByUserIdAndHiddenIsTrue(user.getId());
    List<Todo> todos = new ArrayList<>();
    todos.addAll(notHiddenTodos);
    todos.addAll(usersHiddenTodos);

    return ResponseEntity.ok(todos.stream().map(TodoResponseDto::new).toList());
  }

  @Transactional
  public ResponseEntity<TodoResponseDto> updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    if (!Objects.equals(todo.getUser().getId(), user.getId())) {
      throw new IllegalArgumentException("해당 유저의 Todo가 아닙니다.");
    }

    todo.update(requestDto);

    return ResponseEntity.ok(new TodoResponseDto(todo));
  }

  @Transactional
  public ResponseEntity<String> deleteTodo(Long todoId, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    if (!Objects.equals(todo.getUser().getId(), user.getId())) {
      throw new IllegalArgumentException("todo 삭제 실패");
    }

    todoRepository.deleteById(todoId);
    return ResponseEntity.ok().build();
  }

}
