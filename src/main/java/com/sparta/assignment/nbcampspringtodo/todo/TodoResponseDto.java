package com.sparta.assignment.nbcampspringtodo.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponseDto {

  Long todoId;
  String title;
  String content;
  boolean completed;
  boolean hidden;

  String errorMessage;

  public TodoResponseDto(TodoRequestDto requestDto, String errorMessage) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.completed = requestDto.isCompleted();
    this.hidden = requestDto.isHidden();

    this.errorMessage = errorMessage;
  }

  public TodoResponseDto(Todo savedTodo) {
    this.todoId = savedTodo.getTodoId();
    this.title = savedTodo.getTitle();
    this.content = savedTodo.getContent();

    this.completed = savedTodo.isCompleted();
    this.hidden = savedTodo.isHidden();
  }

}

