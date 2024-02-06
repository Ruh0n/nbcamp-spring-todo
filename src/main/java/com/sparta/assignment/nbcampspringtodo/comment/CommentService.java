package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.common.UserVerifier;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.TodoRepository;
import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final UserVerifier userVerifier;
  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;

  @Transactional
  public CommentResponseDto createComment(
      CommentRequestDto requestDto, Long todoId, String username
  ) {

    User user = userRepository.findByUsernameOrElseThrow(username);

    Todo todo = todoRepository.findByIdOrElseThrow(todoId);

    Comment comment = new Comment(requestDto, user, todo);

    return new CommentResponseDto(commentRepository.save(comment));
  }

  @Transactional
  public CommentResponseDto updateComment(
      CommentRequestDto requestDto, Long commentId, String username
  ) {
    Comment comment = userVerifier.verifyCommentWithUser(commentId, username);

    comment.update(requestDto);

    return new CommentResponseDto(comment);
  }

  @Transactional
  public String deleteComment(Long commentId, String username) {
    Comment comment = userVerifier.verifyCommentWithUser(commentId, username);

    commentRepository.deleteById(comment.getId());

    return "comment: " + comment.getContent();
  }

}
