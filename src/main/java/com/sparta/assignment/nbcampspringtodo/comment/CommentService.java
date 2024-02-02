package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.TodoRepository;
import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final TodoRepository todoRepository;

  @Transactional
  public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
      CommentRequestDto requestDto, Long todoId, String username
  ) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    Comment comment = commentRepository.save(new Comment(requestDto, user, todo));

    return ResponseEntity.ok(ResponseDto.<CommentResponseDto>builder()
                                 .httpStatus(HttpStatus.OK)
                                 .message("comment 등록 성공")
                                 .data(new CommentResponseDto(comment))
                                 .build());
  }

  public ResponseEntity<ResponseDto<List<CommentResponseDto>>> getCommentsByTodoId(Long todoId) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    List<Comment> comments = commentRepository.findAllByTodo_TodoId(todo.getTodoId());

    return ResponseEntity.ok(ResponseDto.<List<CommentResponseDto>>builder()
                                 .httpStatus(HttpStatus.OK)
                                 .message("comment 조회 성공")
                                 .data(comments.stream().map(CommentResponseDto::new).toList())
                                 .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
      CommentRequestDto requestDto, Long commentId, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NullPointerException("comment를 찾을 수 없음"));

    if (!Objects.equals(user.getId(), comment.getUser().getId())) {
      throw new IllegalArgumentException("해당 유저의 Comment가 아닙니다.");
    }

    comment.update(requestDto);

    return ResponseEntity.ok(ResponseDto.<CommentResponseDto>builder()
                                 .httpStatus(HttpStatus.OK)
                                 .message("comment 수정 성공")
                                 .data(new CommentResponseDto(comment))
                                 .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteComment(Long commentId, String username) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NullPointerException("comment를 찾을 수 없음"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
    if (!Objects.equals(user.getId(), comment.getUser().getId())) {
      throw new IllegalArgumentException("comment 삭제 실패");
    }

    commentRepository.deleteById(commentId);

    return ResponseEntity.ok(ResponseDto.<String>builder()
                                 .httpStatus(HttpStatus.OK)
                                 .message("comment 삭제 성공")
                                 .build());
  }

}
