package com.sparta.assignment.nbcampspringtodo.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

  @NotBlank(message = "내용은 공백일 수 없습니다.")
  private String content;

}
