package com.sparta.assignment.nbcampspringtodo.todo.dto;

import com.sparta.assignment.nbcampspringtodo.todo.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseDto {

  Long todoId;
  String title;
  String content;

  String errorMessage;

  public TodoResponseDto(TodoRequestDto requestDto, String errorMessage) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();

    this.errorMessage = errorMessage;
  }

  public TodoResponseDto(Todo savedTodo) {
    this.todoId = savedTodo.getTodoId();
    this.title = savedTodo.getTitle();
    this.content = savedTodo.getContent();
  }

}
