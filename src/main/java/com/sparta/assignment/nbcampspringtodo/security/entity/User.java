package com.sparta.assignment.nbcampspringtodo.security.entity;

import com.sparta.assignment.nbcampspringtodo.comment.entity.Comment;
import com.sparta.assignment.nbcampspringtodo.common.entity.Timestamped;
import com.sparta.assignment.nbcampspringtodo.todo.entity.Todo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private final List<Todo> todos = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private final List<Comment> comments = new ArrayList<>();

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void addTodo(Todo todo) {
    todo.setUser(this);

    this.todos.add(todo);
  }

}
