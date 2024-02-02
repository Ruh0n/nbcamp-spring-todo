package com.sparta.assignment.nbcampspringtodo.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {

  @NotBlank(message = "제목은 필수 입력입니다.")
  String title;

  @NotBlank(message = "내용은 필수 입력입니다.")
  String content;

  boolean completed = false;

  boolean hidden = false;

}