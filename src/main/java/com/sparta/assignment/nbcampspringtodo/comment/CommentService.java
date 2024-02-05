package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.common.Verifier;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final Verifier verifier;
  private final CommentRepository commentRepository;

  @Transactional
  public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
      CommentRequestDto requestDto, Long todoId, String username
  ) {
    User user = verifier.verifyUser(username);
    Todo todo = verifier.verifyTodo(todoId);

    Comment comment = new Comment(requestDto, user, todo);

    return ResponseEntity.ok(ResponseDto.<CommentResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("comment 등록 성공")
        .data(new CommentResponseDto(commentRepository.save(comment)))
        .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
      CommentRequestDto requestDto, Long commentId, String username
  ) {
    Comment comment = verifier.verifyCommentWithUser(commentId, username);

    comment.update(requestDto);

    return ResponseEntity.ok(ResponseDto.<CommentResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("comment 수정 성공")
        .data(new CommentResponseDto(comment))
        .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteComment(Long commentId, String username) {
    Comment comment = verifier.verifyCommentWithUser(commentId, username);

    commentRepository.deleteById(comment.getId());

    return ResponseEntity.ok(ResponseDto.<String>builder()
        .httpStatus(HttpStatus.OK)
        .message("comment 삭제 성공")
        .build());
  }

}
