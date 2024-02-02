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
        .orElseThrow(() -> new NullPointerException("해당 TODO를 찾을 수 없습니다."));

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    Comment comment = new Comment(requestDto, user, todo);

    Comment savedComment = commentRepository.save(comment);

    return ResponseEntity.ok(new CommentResponseDto(savedComment));
  }

  @Transactional
  public ResponseEntity<CommentResponseDto> updateComment(
      CommentRequestDto requestDto, Long commentId, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NullPointerException("해당 Comment를 찾을 수 없습니다."));

    if (!Objects.equals(user.getId(), comment.getUser().getId())) {
      throw new IllegalArgumentException("해당 유저의 Comment가 아닙니다.");
    }

    comment.update(requestDto);

    return ResponseEntity.ok(new CommentResponseDto(commentRepository.save(comment)));
  }

  public ResponseEntity<String> deleteComment(Long commentId, String username) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NullPointerException("해당 Comment를 찾을 수 없습니다."));
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));
    if (!Objects.equals(user.getId(), comment.getUser().getId())) {
      throw new IllegalArgumentException("해당 유저의 Comment가 아닙니다.");
    }

    commentRepository.deleteById(commentId);
    return ResponseEntity.ok("댓글이 삭제되었습니다.");

  }

  public ResponseEntity<List<CommentResponseDto>> getCommentByTodoId(Long todoId) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new NullPointerException("해당 TODO를 찾을 수 없습니다."));

    List<Comment> comments = commentRepository.findAllByTodo_TodoId(todoId);

    return ResponseEntity.ok(comments.stream().map(CommentResponseDto::new).toList());
  }

}
