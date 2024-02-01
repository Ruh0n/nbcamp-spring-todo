package com.sparta.assignment.nbcampspringtodo.todo.service;

import com.sparta.assignment.nbcampspringtodo.security.entity.User;
import com.sparta.assignment.nbcampspringtodo.security.repository.UserRepository;
import com.sparta.assignment.nbcampspringtodo.todo.dto.TodoRequestDto;
import com.sparta.assignment.nbcampspringtodo.todo.dto.TodoResponseDto;
import com.sparta.assignment.nbcampspringtodo.todo.entity.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.repsitory.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TodoService {

  private UserRepository userRepository;
  private TodoRepository todoRepository;

  @Transactional
  public ResponseEntity<TodoResponseDto> createTodo(TodoRequestDto requestDto, String username) {
    User user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    Todo savedTodo = todoRepository.save(new Todo(requestDto, user));

    return ResponseEntity.ok(new TodoResponseDto(savedTodo));
  }

  public ResponseEntity<TodoResponseDto> updateTodo(
      Long todoId, TodoRequestDto requestDto, String username
  ) {
    Todo existingTodo = todoRepository
        .findById(todoId)
        .orElseThrow(() -> new NullPointerException("해당 Todo를 찾을 수 없습니다."));

    User user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    return null;
  }

}