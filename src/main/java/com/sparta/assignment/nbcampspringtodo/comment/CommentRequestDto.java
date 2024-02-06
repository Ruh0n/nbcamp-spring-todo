package com.sparta.assignment.nbcampspringtodo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

  @Schema(example = "Sample Content")
  @NotBlank(message = "내용은 공백일 수 없습니다.")
  private String content;

}
