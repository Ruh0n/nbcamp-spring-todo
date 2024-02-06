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
public class UserVerifier {

  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;
  private final PasswordEncoder passwordEncoder;

  public User verifyUserWithPassword(String username, String password) {
    User user = userRepository.findByUsernameOrElseThrow(username);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("잘못된 password");
    }

    return user;
  }

  public Todo verifyTodoWithUser(Long todoId, String username) {
    User user = userRepository.findByUsernameOrElseThrow(username);
    Todo todo = todoRepository.findByIdOrElseThrow(todoId);

    assertEqualUsers(user, todo.getUser());

    return todo;
  }

  public Comment verifyCommentWithUser(Long commentId, String username) {
    User user = userRepository.findByUsernameOrElseThrow(username);
    Comment comment = commentRepository.findByIdOrElseThrow(commentId);

    assertEqualUsers(user, comment.getUser());

    return comment;
  }

  private void assertEqualUsers(User user1, User user2) {
    if (!Objects.equals(user1.getId(), user2.getId())) {
      throw new IllegalArgumentException("잘못된 접근");
    }
  }

}
