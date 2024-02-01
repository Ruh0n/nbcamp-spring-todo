package com.sparta.assignment.nbcampspringtodo.comment.service;

import com.sparta.assignment.nbcampspringtodo.comment.dto.CommentRequestDto;
import com.sparta.assignment.nbcampspringtodo.comment.dto.CommentResponseDto;
import com.sparta.assignment.nbcampspringtodo.comment.entity.Comment;
import com.sparta.assignment.nbcampspringtodo.comment.repository.CommentRepository;
import com.sparta.assignment.nbcampspringtodo.security.entity.User;
import com.sparta.assignment.nbcampspringtodo.security.repository.UserRepository;
import com.sparta.assignment.nbcampspringtodo.todo.entity.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.repsitory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final TodoRepository todoRepository;

  public ResponseEntity<CommentResponseDto> createComment(
      CommentRequestDto requestDto, Long todoId, String username
  ) {
    Todo todo = todoRepository
        .findById(todoId)
        .orElseThrow(() -> new NullPointerException("해당 TODO를 찾을 수 없습니다."));

    User user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    Comment comment = new Comment(requestDto);
    user.addComment(comment);
    todo.addComment(comment);

    Comment savedComment = commentRepository.save(comment);

    return ResponseEntity.ok(new CommentResponseDto(savedComment));
  }

}
