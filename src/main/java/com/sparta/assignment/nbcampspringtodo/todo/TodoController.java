package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

  private final TodoService todoService;

  @PostMapping()
  public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
      @Valid @RequestBody TodoRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.createTodo(requestDto, userDetails.getUsername());
  }

  @GetMapping("/userId/{userId}")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodosByUserId(
      @PathVariable Long userId
  ) {
    return todoService.getTodosByUserId(userId);
  }

  @GetMapping()
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getAllNotHiddenTodos(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getAllNotHiddenTodos(userDetails.getUsername());
  }

  @GetMapping("/search")
  public ResponseEntity<ResponseDto<List<TodoResponseDto>>> searchTodoByTitle(
      @Valid @NotBlank @RequestParam("q") String search
  ) {
    return todoService.searchTodoByTitle(search);
  }

  @PutMapping("/todoId/{todoId}")
  public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
      @PathVariable Long todoId,
      @Valid @RequestBody TodoRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.updateTodo(todoId, requestDto, userDetails.getUsername());
  }


  @DeleteMapping("/todoId/{todoId}")
  public ResponseEntity<ResponseDto<String>> deleteTodo(
      @PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.deleteTodo(todoId, userDetails.getUsername());
  }

}
