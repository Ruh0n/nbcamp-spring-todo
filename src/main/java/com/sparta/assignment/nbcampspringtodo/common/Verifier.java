package com.sparta.assignment.nbcampspringtodo.common;

import com.sparta.assignment.nbcampspringtodo.comment.Comment;
import com.sparta.assignment.nbcampspringtodo.comment.CommentRepository;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.TodoRepository;
import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Verifier {

  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;
  private final PasswordEncoder passwordEncoder;

  public User verifyUser(String username) {

    return userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
  }

  public User verifyUser(Long userId) {

    return userRepository.findById(userId)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
  }

  public Todo verifyTodo(Long todoId) {

    return todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));
  }

  public Comment verifyComment(Long commentId) {

    return commentRepository.findById(commentId)
        .orElseThrow(() -> new NullPointerException("comment를 찾을 수 없음"));
  }

  public User verifyUserWithPassword(String username, String password) {
    User user = verifyUser(username);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("잘못된 password");
    }

    return user;
  }

  public Todo verifyTodoWithUser(Long todoId, String username) {
    User user = verifyUser(username);

    Todo todo = verifyTodo(todoId);

    assertEqualUsers(user, todo.getUser());

    return todo;
  }

  public Comment verifyCommentWithUser(Long commentId, String username) {
    User user = verifyUser(username);

    Comment comment = verifyComment(commentId);

    assertEqualUsers(user, comment.getUser());

    return comment;
  }

  private void assertEqualUsers(User user1, User user2) {
    if (!Objects.equals(user1.getId(), user2.getId())) {
      throw new IllegalArgumentException("잘못된 접근");
    }
  }

}
