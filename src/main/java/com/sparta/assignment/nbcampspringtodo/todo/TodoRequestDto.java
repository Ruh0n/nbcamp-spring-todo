package com.sparta.assignment.nbcampspringtodo.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {

  @NotBlank(message = "제목 입력은 필수입니다.")
  String title;

  @NotBlank(message = "내용 입력은 필수입니다.")
  String content;

  boolean completed = false;

  boolean hidden = false;

}
