package com.sparta.assignment.nbcampspringtodo.feature.todo;

import com.sparta.assignment.nbcampspringtodo.common.Timestamped;
import com.sparta.assignment.nbcampspringtodo.feature.user.User;
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
  private Long id;

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
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;


  public Todo(TodoRequestDto requestDto, User user) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.completed = requestDto.isCompleted();
    this.hidden = requestDto.isHidden();

    this.user = user;
  }

  public void update(TodoRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.completed = requestDto.isCompleted();
    this.hidden = requestDto.isHidden();
  }

}
