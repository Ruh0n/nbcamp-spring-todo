package com.sparta.assignment.nbcampspringtodo.todo;

import com.sparta.assignment.nbcampspringtodo.comment.Comment;
import com.sparta.assignment.nbcampspringtodo.common.Timestamped;
import com.sparta.assignment.nbcampspringtodo.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

  @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY)
  private final List<Comment> comments = new ArrayList<>();

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
