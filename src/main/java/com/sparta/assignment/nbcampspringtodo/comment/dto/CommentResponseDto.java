package com.sparta.assignment.nbcampspringtodo.comment.dto;

import com.sparta.assignment.nbcampspringtodo.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private Long commentId;
  private String content;
  private String message;

  public CommentResponseDto(CommentRequestDto requestDto, String message) {
    this.content = requestDto.getContent();
    this.message = message;
  }

  public CommentResponseDto(Comment savedComment) {
    this.commentId = savedComment.getId();
    this.content = savedComment.getContent();
  }

}
