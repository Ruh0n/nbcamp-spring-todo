package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Todo API", description = "Operations about todos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo/v1")
public class TodoController {

  private final TodoService todoService;

  @Operation(summary = "Create a new todo")
  @PostMapping("/todos")
  public ResponseEntity<ResponseDto<TodoDetailResponseDto>> createTodo(
      @Valid @RequestBody TodoRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.createTodo(requestDto, userDetails.getUsername());
  }

  @Operation(summary = "Get not hidden todos")
  @GetMapping("/todos")
  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getAllNotHiddenTodos(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getAllNotHiddenTodos(userDetails.getUsername());
  }

  @Operation(summary = "Get a todo details")
  @GetMapping("/todos/{todoId}")
  public ResponseEntity<ResponseDto<TodoDetailResponseDto>> getTodoDetail(
      @PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getTodoDetail(todoId, userDetails.getUsername());
  }

  @Operation(summary = "Get todos of a user")
  @GetMapping("/user/{userId}/todos")
  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getTodosByUserId(
      @PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getTodosByUserId(userId, userDetails.getUsername());
  }

  @Operation(summary = "Search todos by Title")
  @GetMapping("/todos/search")
  public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> searchTodoByTitle(
      @Valid @NotBlank @RequestParam("q") String search
  ) {
    return todoService.searchTodoByTitle(search);
  }

  @Operation(summary = "Update a todo")
  @PutMapping("/todos/{todoId}")
  public ResponseEntity<ResponseDto<TodoListResponseDto>> updateTodo(
      @PathVariable Long todoId,
      @Valid @RequestBody TodoRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.updateTodo(todoId, requestDto, userDetails.getUsername());
  }


  @Operation(summary = "Delete a todo")
  @DeleteMapping("/todos/{todoId}")
  public ResponseEntity<ResponseDto<String>> deleteTodo(
      @PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.deleteTodo(todoId, userDetails.getUsername());
  }

}
