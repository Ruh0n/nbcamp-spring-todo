package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.comment.CommentRepository;
import com.sparta.assignment.nbcampspringtodo.common.UserVerifier;
import com.sparta.assignment.nbcampspringtodo.todo.Todo;
import com.sparta.assignment.nbcampspringtodo.todo.TodoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserVerifier userVerifier;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TodoRepository todoRepository;
  private final CommentRepository commentRepository;


  @Transactional
  public UserDetailResponseDto signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();

    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("username 중복");
    }

    String password = passwordEncoder.encode(requestDto.getPassword());

    return new UserDetailResponseDto(userRepository.save(new User(username, password)));
  }

  @Transactional
  public String deleteUser(
      DeleteUserRequestDto requestDto, String username
  ) {
    User user = userVerifier.verifyUserWithPassword(username, requestDto.getPassword());

    List<Todo> todos = todoRepository.findAllByUserId(user.getId());
    todos.forEach(t -> commentRepository.deleteAll(commentRepository.findAllByTodo_id(t.getId())));

    todoRepository.deleteAll(todos);

    userRepository.delete(user);

    return username + " user 삭제";
  }

}
