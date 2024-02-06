package com.sparta.assignment.nbcampspringtodo.feature.todo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TodoRequestDto {

  @Schema(defaultValue = "Sample Title")
  @NotBlank(message = "제목 입력은 필수입니다.") String title;

  @Schema(defaultValue = "Sample content")
  @NotBlank(message = "내용 입력은 필수입니다.") String content;

  @Schema(defaultValue = "false")
  boolean completed = false;
  @Schema(defaultValue = "false")
  boolean hidden = false;

}
