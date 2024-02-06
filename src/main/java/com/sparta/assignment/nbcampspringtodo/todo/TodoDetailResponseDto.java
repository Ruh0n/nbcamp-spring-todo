package com.sparta.assignment.nbcampspringtodo.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.assignment.nbcampspringtodo.comment.Comment;
import com.sparta.assignment.nbcampspringtodo.comment.CommentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDetailResponseDto {

  private final Long id;
  @Schema(defaultValue = "Sample Title")
  private final String title;
  @Schema(defaultValue = "Sample Content")
  private final String content;

  @Schema(defaultValue = "false")
  private final boolean completed;
  @Schema(defaultValue = "false")
  private final boolean hidden;

  private final LocalDateTime createdDate;
  @Schema(defaultValue = "username")
  private final String author;

  private final List<CommentResponseDto> comments = new ArrayList<>();


  public TodoDetailResponseDto(Todo todo) {
    this(todo, new ArrayList<>());
  }

  public TodoDetailResponseDto(Todo todo, List<Comment> comments) {
    this.id = todo.getId();
    this.title = todo.getTitle();
    this.content = todo.getContent();

    this.completed = todo.isCompleted();
    this.hidden = todo.isHidden();

    this.createdDate = todo.getCreatedDate();
    this.author = todo.getUser().getUsername();

    this.comments.addAll(comments.stream().map(CommentResponseDto::new).toList());
  }

}

