package com.sparta.assignment.nbcampspringtodo.comment;

import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.TodoRepository;
import com.sparta.assignment.nbcampspringtodo.user.User;
import com.sparta.assignment.nbcampspringtodo.user.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<CommentResponseDto> createComment(
      CommentRequestDto requestDto, Long todoId, String username
  ) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    Comment comment = new Comment(requestDto, user, todo);

    Comment savedComment = commentRepository.save(comment);

    return ResponseEntity.ok(new CommentResponseDto(savedComment));
  }

  public ResponseEntity<List<CommentResponseDto>> getCommentsByTodoId(Long todoId) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("todo를 찾을 수 없음"));

    List<Comment> comments = commentRepository.findAllByTodo_TodoId(todo.getTodoId());

    return ResponseEntity.ok(comments.stream().map(CommentResponseDto::new).toList());
  }

  @Transactional
  public ResponseEntity<CommentResponseDto> updateComment(
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

    return ResponseEntity.ok(new CommentResponseDto(commentRepository.save(comment)));
  }

  @Transactional
  public ResponseEntity<String> deleteComment(Long commentId, String username) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NullPointerException("comment를 찾을 수 없음"));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));
    if (!Objects.equals(user.getId(), comment.getUser().getId())) {
      throw new IllegalArgumentException("comment 삭제 실패");
    }

    commentRepository.deleteById(commentId);
    return ResponseEntity.ok("comment 삭제 성공");

  }

}
