package com.sparta.assignment.nbcampspringtodo.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.assignment.nbcampspringtodo.comment.Comment;
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


  public TodoDetailResponseDto(Todo savedTodo) {
    this.todoId = savedTodo.getTodoId();
    this.title = savedTodo.getTitle();
    this.content = savedTodo.getContent();

    this.completed = savedTodo.isCompleted();
    this.hidden = savedTodo.isHidden();

    this.createdDate = savedTodo.getCreatedDate();
    this.author = savedTodo.getUser().getUsername();
  }

  public TodoDetailResponseDto(Todo savedTodo, List<Comment> comments) {
    this.todoId = savedTodo.getTodoId();
    this.title = savedTodo.getTitle();
    this.content = savedTodo.getContent();

    this.completed = savedTodo.isCompleted();
    this.hidden = savedTodo.isHidden();

    this.createdDate = savedTodo.getCreatedDate();
    this.author = savedTodo.getUser().getUsername();

    this.comments.addAll(comments.stream().map(CommentResponseDto::new).toList());
  }

}

