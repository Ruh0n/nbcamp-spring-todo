package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

  @PostMapping()
  public ResponseEntity<TodoResponseDto> createTodo(
      @Valid @RequestBody TodoRequestDto requestDto,
      BindingResult bindingResult,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      fieldErrors.forEach(e -> log.error(e.getField() + ": " + e.getDefaultMessage()));

      return ResponseEntity.badRequest().body(new TodoResponseDto(requestDto, "TODO 등록에 실패했습니다."));
    }

    return todoService.createTodo(requestDto, userDetails.getUsername());
  }

  private final TodoService todoService;

  @GetMapping()
  public ResponseEntity<List<TodoResponseDto>> getAllNotHiddenTodos(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.getAllNotHiddenTodos(userDetails.getUsername());
  }

  @GetMapping("/userId/{userId}")
  public ResponseEntity<List<TodoResponseDto>> getTodosByUserId(
      @PathVariable Long userId
  ) {
    return todoService.getTodosByUserId(userId);
  }

  @GetMapping("/search")
  public ResponseEntity<List<TodoResponseDto>> searchTodoByTitle(
      @RequestParam("q") String search
  ) {
    return todoService.searchTodoByTitle(search);
  }

  @PutMapping("/todoId/{todoId}")
  public ResponseEntity<TodoResponseDto> updateTodo(
      @PathVariable Long todoId,
      @Valid @RequestBody TodoRequestDto requestDto,
      BindingResult bindingResult,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      fieldErrors.forEach(e -> log.error(e.getField() + ": " + e.getDefaultMessage()));

      return ResponseEntity.badRequest().body(new TodoResponseDto(requestDto, "TODO 수정에 실패했습니다."));
    }

    return todoService.updateTodo(todoId, requestDto, userDetails.getUsername());
  }


  @DeleteMapping("/todoId/{todoId}")
  public ResponseEntity<String> deleteTodo(
      @PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return todoService.deleteTodo(todoId, userDetails.getUsername());
  }

}
