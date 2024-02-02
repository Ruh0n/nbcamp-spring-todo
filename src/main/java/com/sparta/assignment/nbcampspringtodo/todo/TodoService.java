package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
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

  // TODO 항상 반환 타입이 ok 인것 수정

  @Transactional
  public ResponseEntity<TodoResponseDto> createTodo(TodoRequestDto requestDto, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    Todo newTodo = new Todo(requestDto, user);

    return ResponseEntity.ok(new TodoResponseDto(todoRepository.save(newTodo)));
  }

  @Transactional
  public ResponseEntity<TodoResponseDto> updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("해당 Todo를 찾을 수 없습니다."));

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    if (!Objects.equals(todo.getUser().getId(), user.getId())) {
      throw new IllegalArgumentException("해당 유저의 Todo가 아닙니다.");
    }

    todo.update(requestDto);

    return ResponseEntity.ok(new TodoResponseDto(todo));
  }

  public ResponseEntity<List<TodoResponseDto>> getTodosByUserId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    List<Todo> todos = todoRepository.findAllByUserId(user.getId());

    return ResponseEntity.ok(todos.stream().map(TodoResponseDto::new).toList());
  }

}
