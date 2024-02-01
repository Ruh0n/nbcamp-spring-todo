package com.sparta.assignment.nbcampspringtodo.security.service;

import com.sparta.assignment.nbcampspringtodo.security.dto.SignupRequestDto;
import com.sparta.assignment.nbcampspringtodo.security.dto.SignupResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.entity.User;
import com.sparta.assignment.nbcampspringtodo.security.jwt.JwtUtil;
import com.sparta.assignment.nbcampspringtodo.security.repository.UserRepository;
import com.sparta.assignment.nbcampspringtodo.security.security.UserDetailsImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;


  @Transactional
  public ResponseEntity<SignupResponseDto> signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    String message = "";
    HttpStatusCode statusCode = HttpStatus.OK;

    // 이름 중복 확인
    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      message = "중복된 username 입니다.";
      statusCode = HttpStatus.BAD_REQUEST;
    } else {
      User NewUser = new User(username, password);
      userRepository.save(NewUser);
    }

    return ResponseEntity.status(statusCode).body(new SignupResponseDto(username, message));
  }

  public ResponseEntity<String> deleteUser(UserDetailsImpl userDetails) {
    String username = userDetails.getUsername();
    String password = passwordEncoder.encode(userDetails.getPassword());

    User user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new NullPointerException("해당 유저를 찾을 수 없습니다."));

    user.getTodos().clear();
    userRepository.delete(user);

    return ResponseEntity.ok(username + " 회원이 성공적으로 삭제되었습니다.");
  }

}
