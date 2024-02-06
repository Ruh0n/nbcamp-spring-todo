package com.sparta.assignment.nbcampspringtodo.feature.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.assignment.nbcampspringtodo.feature.user.UserDetailResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponseDto {

  private final Long id;
  @Schema(defaultValue = "Sample Title")
  private final String title;

  private final boolean completed;
  private final boolean hidden;

  private final LocalDateTime createdDate;
  private final UserDetailResponseDto user;


  public TodoResponseDto(Todo savedTodo) {
    this.id = savedTodo.getId();
    this.title = savedTodo.getTitle();

    this.completed = savedTodo.isCompleted();
    this.hidden = savedTodo.isHidden();

    this.createdDate = savedTodo.getCreatedDate();
    this.user = new UserDetailResponseDto(savedTodo.getUser());
  }

}

