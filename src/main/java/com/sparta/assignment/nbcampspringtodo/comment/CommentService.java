package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.common.Verifier;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final Verifier verifier;
  private final CommentRepository commentRepository;

  @Transactional
  public CommentResponseDto createComment(
      CommentRequestDto requestDto, Long todoId, String username
  ) {
    User user = verifier.verifyUser(username);
    Todo todo = verifier.verifyTodo(todoId);

    Comment comment = new Comment(requestDto, user, todo);

    return new CommentResponseDto(commentRepository.save(comment));
  }

  @Transactional
  public CommentResponseDto updateComment(
      CommentRequestDto requestDto, Long commentId, String username
  ) {
    Comment comment = verifier.verifyCommentWithUser(commentId, username);

    comment.update(requestDto);

    return new CommentResponseDto(comment);
  }

  @Transactional
  public String deleteComment(Long commentId, String username) {
    Comment comment = verifier.verifyCommentWithUser(commentId, username);

    commentRepository.deleteById(comment.getId());

    return "comment: " + comment.getContent();
  }

}
