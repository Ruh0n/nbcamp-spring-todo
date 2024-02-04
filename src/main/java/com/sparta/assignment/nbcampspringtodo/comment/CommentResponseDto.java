package com.sparta.assignment.nbcampspringtodo.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

  private final String content;
  private Long commentId;


  public CommentResponseDto(CommentRequestDto requestDto) {
    this.content = requestDto.getContent();
  }

  public CommentResponseDto(Comment savedComment) {
    this.commentId = savedComment.getId();
    this.content = savedComment.getContent();
  }

}
