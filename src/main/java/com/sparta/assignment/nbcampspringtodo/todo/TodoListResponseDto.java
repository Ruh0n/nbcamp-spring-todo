package com.sparta.assignment.nbcampspringtodo.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoListResponseDto {

  private final Long id;
  @Schema(defaultValue = "Sample Title")
  private final String title;

  private final boolean completed;
  private final boolean hidden;

  private final LocalDateTime createdDate;
  private final String author;


  public TodoListResponseDto(Todo savedTodo) {
    this.id = savedTodo.getId();
    this.title = savedTodo.getTitle();

    this.completed = savedTodo.isCompleted();
    this.hidden = savedTodo.isHidden();

    this.createdDate = savedTodo.getCreatedDate();
    this.author = savedTodo.getUser().getUsername();
  }

}

