package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/v1")
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/todos/{todoId}/comments")
  public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
      @PathVariable Long todoId,
      @Valid @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return commentService.createComment(requestDto, todoId, userDetails.getUsername());
  }

  @PutMapping("/comments/{commentId}")
  public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
      @PathVariable Long commentId,
      @Valid @RequestBody CommentRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return commentService.updateComment(requestDto, commentId, userDetails.getUsername());
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<ResponseDto<String>> deleteComment(
      @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return commentService.deleteComment(commentId, userDetails.getUsername());
  }

}
