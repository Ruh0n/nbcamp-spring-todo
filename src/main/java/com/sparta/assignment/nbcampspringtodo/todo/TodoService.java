package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.common.Verifier;
import com.sparta.assignment.nbcampspringtodo.user.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

  private final Verifier verifier;
  private TodoRepository todoRepository;

  @Transactional
  public ResponseEntity<ResponseDto<TodoDetailResponseDto>> createTodo(
      TodoRequestDto requestDto, String username
  ) {
    User user = verifier.verifyUser(username);

    return ResponseEntity.ok(ResponseDto.<TodoDetailResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 등록 성공")
        .data(new TodoDetailResponseDto(todoRepository.save(new Todo(requestDto, user))))
        .build());
  }

  public ResponseEntity<ResponseDto<TodoDetailResponseDto>> getTodoDetail(
      Long todoId, String username
  ) {
    Todo todo = verifier.verifyTodoWithUser(todoId, username);

    return ResponseEntity.ok(ResponseDto.<TodoDetailResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(new TodoDetailResponseDto(todo))
        .build());
  }

  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getTodosByUserId(Long userId) {
    User user = verifier.verifyUser(userId);

    List<Todo> todos = todoRepository.findAllByUserId(user.getId());

    return ResponseEntity.ok(ResponseDto.<List<TodoListResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todos.stream().map(TodoListResponseDto::new).toList())
        .build());
  }

  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getAllNotHiddenTodos(String username) {
    User user = verifier.verifyUser(username);
    List<Todo> todos = todoRepository.findAllByHiddenIsFalseOrUserId(user.getId());

    ResponseDto<List<TodoListResponseDto>> responseDto = ResponseDto.<List<TodoListResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todos.stream().map(TodoListResponseDto::new).toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> searchTodoByTitle(String search) {
    List<Todo> todos = todoRepository.findAllByTitleContainsAndHiddenIsFalse(search);

    return ResponseEntity.ok(ResponseDto.<List<TodoListResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todos.stream().map(TodoListResponseDto::new).toList())
        .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<TodoListResponseDto>> updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    Todo todo = verifier.verifyTodoWithUser(todoId, username);

    todo.update(requestDto);

    return ResponseEntity.ok(ResponseDto.<TodoListResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 수정 성공")
        .data(new TodoListResponseDto(todo))
        .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteTodo(Long todoId, String username) {
    Todo todo = verifier.verifyTodoWithUser(todoId, username);

    todoRepository.deleteById(todo.getTodoId());

    return ResponseEntity.ok(ResponseDto.<String>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 삭제 성공")
        .build());
  }

}
