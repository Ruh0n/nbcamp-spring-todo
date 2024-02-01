package com.sparta.assignment.nbcampspringtodo.comment.entity;

import com.sparta.assignment.nbcampspringtodo.comment.dto.CommentRequestDto;
import com.sparta.assignment.nbcampspringtodo.common.entity.Timestamped;
import com.sparta.assignment.nbcampspringtodo.security.entity.User;
import com.sparta.assignment.nbcampspringtodo.todo.entity.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @Setter
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @Setter
  @JoinColumn(name = "todo_id")
  private Todo todo;

  public Comment(CommentRequestDto requestDto) {
    this.content = requestDto.getContent();
  }

}
