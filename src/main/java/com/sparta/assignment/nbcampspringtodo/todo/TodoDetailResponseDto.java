package com.sparta.assignment.nbcampspringtodo.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.assignment.nbcampspringtodo.comment.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDetailResponseDto {

  private final Long todoId;
  private final String title;
  private final String content;

  private final boolean completed;
  private final boolean hidden;

  private final LocalDateTime createdDate;
  private final String author;

  private final List<CommentResponseDto> comments = new ArrayList<>();


  public TodoDetailResponseDto(Todo todo) {
    this.todoId = todo.getId();
    this.title = todo.getTitle();
    this.content = todo.getContent();

    this.completed = todo.isCompleted();
    this.hidden = todo.isHidden();

    this.createdDate = todo.getCreatedDate();
    this.author = todo.getUser().getUsername();

    this.comments.addAll(todo.getComments().stream().map(CommentResponseDto::new).toList());
  }

}

