package com.sparta.assignment.nbcampspringtodo.comment.Controller;

import com.sparta.assignment.nbcampspringtodo.comment.dto.CommentRequestDto;
import com.sparta.assignment.nbcampspringtodo.comment.dto.CommentResponseDto;
import com.sparta.assignment.nbcampspringtodo.comment.service.CommentService;
import com.sparta.assignment.nbcampspringtodo.security.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

  private final CommentService commentService;

  @PutMapping("/todoId/{todoId}")
  public ResponseEntity<CommentResponseDto> createComment(
      @PathVariable Long todoId,
      @Valid @RequestBody CommentRequestDto requestDto,
      BindingResult bindingResult,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    if (bindingResult.hasErrors()) {
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      fieldErrors.forEach(e -> log.error(e.getField() + ": " + e.getDefaultMessage()));

      return ResponseEntity
          .badRequest()
          .body(new CommentResponseDto(requestDto, "Comment 등록에 실패했습니다."));
    }
    return commentService.createComment(requestDto, todoId, userDetails.getUsername());
  }

}
