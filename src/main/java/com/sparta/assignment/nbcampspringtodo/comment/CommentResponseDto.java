package com.sparta.assignment.nbcampspringtodo.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
