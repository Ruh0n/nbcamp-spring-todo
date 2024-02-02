package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.common.Timestamped;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.user.User;
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

@Entity
@Getter
@NoArgsConstructor
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

  public Comment(CommentRequestDto requestDto, User user, Todo todo) {
    this.content = requestDto.getContent();
    this.user = user;
  }

  public void update(CommentRequestDto requestDto) {
    this.content = requestDto.getContent();
  }

}
