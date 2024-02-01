package com.sparta.assignment.nbcampspringtodo.todo.controller;

import com.sparta.assignment.nbcampspringtodo.security.security.UserDetailsImpl;
import com.sparta.assignment.nbcampspringtodo.todo.dto.TodoRequestDto;
import com.sparta.assignment.nbcampspringtodo.todo.dto.TodoResponseDto;
import com.sparta.assignment.nbcampspringtodo.todo.service.TodoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

  private final TodoService todoService;

  @PutMapping()
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

  @PostMapping("/todoId/{todoId}")
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

}
