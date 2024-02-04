package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.comment.Comment;
import com.sparta.assignment.nbcampspringtodo.comment.CommentRepository;
import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
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
  private final CommentRepository commentRepository;


  @Transactional
  public ResponseEntity<ResponseDto<TodoDetailResponseDto>> createTodo(
      TodoRequestDto requestDto, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    ResponseDto<TodoDetailResponseDto> responseDto = ResponseDto.<TodoDetailResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 등록 성공")
        .data(new TodoDetailResponseDto(todoRepository.save(new Todo(requestDto, user))))
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<TodoDetailResponseDto>> getTodoDetail(
      Long todoId, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    if (todo.isHidden() && (!Objects.equals(todo.getUser(), user))) {
      throw new IllegalArgumentException("잘못된 접근");
    }

    List<Comment> comments = commentRepository.findAllByTodo_TodoId(todoId);

    ResponseDto<TodoDetailResponseDto> responseDto = ResponseDto.<TodoDetailResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(new TodoDetailResponseDto(todo, comments))
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getTodosByUserId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    ResponseDto<List<TodoListResponseDto>> responseDto = ResponseDto.<List<TodoListResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todoRepository.findAllByUserId(user.getId())
            .stream()
            .map(TodoListResponseDto::new)
            .toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getAllNotHiddenTodos(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
    List<Todo> todos = todoRepository.findAllByHiddenIsFalseOrUserId(user.getId());

    ResponseDto<List<TodoListResponseDto>> responseDto = ResponseDto.<List<TodoListResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todos.stream().map(TodoListResponseDto::new).toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> searchTodoByTitle(String search) {
    ResponseDto<List<TodoListResponseDto>> responseDto = ResponseDto.<List<TodoListResponseDto>>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 조회 성공")
        .data(todoRepository.findAllByTitleContainsAndHiddenIsFalse(search)
            .stream()
            .map(TodoListResponseDto::new)
            .toList())
        .build();

    return ResponseEntity.ok(responseDto);
  }

  @Transactional
  public ResponseEntity<ResponseDto<TodoListResponseDto>> updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    if (!Objects.equals(todo.getUser().getId(), user.getId())) {
      throw new IllegalArgumentException("잘못된 접근");
    }

    todo.update(requestDto);

    ResponseDto<TodoListResponseDto> responseDto = ResponseDto.<TodoListResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 수정 성공")
        .data(new TodoListResponseDto(todo))
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
      throw new IllegalArgumentException("잘못된 접근");
    }

    todoRepository.deleteById(todoId);

    ResponseDto<String> responseDto = ResponseDto.<String>builder()
        .httpStatus(HttpStatus.OK)
        .message("todo 삭제 성공")
        .build();

    return ResponseEntity.ok(responseDto);
  }

}
