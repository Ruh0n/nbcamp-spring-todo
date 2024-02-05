package com.sparta.assignment.nbcampspringtodo.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

  private final Long id;

  @Schema(example = "Sample Content")
  private final String content;
  @Schema(example = "username")
  private final String author;


  public CommentResponseDto(Comment savedComment) {
    this.id = savedComment.getId();
    this.content = savedComment.getContent();
    this.author = savedComment.getUser().getUsername();
  }

}
