package com.sparta.assignment.nbcampspringtodo.todo.entity;

import com.sparta.assignment.nbcampspringtodo.common.entity.Timestamped;
import com.sparta.assignment.nbcampspringtodo.security.entity.User;
import com.sparta.assignment.nbcampspringtodo.todo.dto.TodoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Todo extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long todoId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private boolean completed;

  @Column(nullable = false)
  private boolean hidden;

  @Setter
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Todo(TodoRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.completed = requestDto.isCompleted();
    this.hidden = requestDto.isHidden();
  }

  public void update(TodoRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.completed = requestDto.isCompleted();
    this.hidden = requestDto.isHidden();
  }

}
