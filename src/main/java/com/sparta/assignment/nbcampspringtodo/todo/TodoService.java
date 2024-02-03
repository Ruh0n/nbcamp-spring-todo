package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

  private UserRepository userRepository;
  private TodoRepository todoRepository;


  @Transactional
  public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
      TodoRequestDto requestDto, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    ResponseDto<TodoResponseDto> responseDto = ResponseDto.<TodoResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 등록 성공")
        .data(new TodoResponseDto(todoRepository.save(new Todo(requestDto, user))))
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodosByUserId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    ResponseDto<List<TodoResponseDto>> responseDto = ResponseDto.<List<TodoResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todoRepository.findAllByUserId(user.getId())
                  .stream()
                  .map(TodoResponseDto::new)
                  .toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllNotHiddenTodos(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    List<Todo> notHiddenTodos = todoRepository.findAllByHiddenIsFalse();
    List<Todo> usersHiddenTodos = todoRepository.findAllByUserIdAndHiddenIsTrue(user.getId());
    List<Todo> todos = new ArrayList<>();
    todos.addAll(notHiddenTodos);
    todos.addAll(usersHiddenTodos);

    ResponseDto<List<TodoResponseDto>> responseDto = ResponseDto.<List<TodoResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todos.stream().map(TodoResponseDto::new).toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> searchTodoByTitle(String search) {
    ResponseDto<List<TodoResponseDto>> responseDto = ResponseDto.<List<TodoResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todoRepository.findAllByTitleContainsAndHiddenIsFalse(search)
                  .stream()
                  .map(TodoResponseDto::new)
                  .toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  @Transactional
  public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    if (!Objects.equals(todo.getUser().getId(), user.getId())) {
      throw new IllegalArgumentException("해당 user의 todo가 아닙니다.");
    }

    todo.update(requestDto);

    ResponseDto<TodoResponseDto> responseDto = ResponseDto.<TodoResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 수정 성공")
        .data(new TodoResponseDto(todo))
        .build();

    return ResponseEntity.ok(responseDto);
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteTodo(Long todoId, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    if (!Objects.equals(todo.getUser().getId(), user.getId())) {
      throw new IllegalArgumentException("todo 삭제 실패");
    }

    todoRepository.deleteById(todoId);

    ResponseDto<String> responseDto = ResponseDto.<String>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 삭제 성공")
        .build();

    return ResponseEntity.ok(responseDto);
  }

}
