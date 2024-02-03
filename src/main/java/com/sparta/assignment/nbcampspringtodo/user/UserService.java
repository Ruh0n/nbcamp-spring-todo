package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public ResponseEntity<SignupResponseDto> signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    // 이름 중복 확인
    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("username 중복");
    }
    User NewUser = new User(username, password);
    userRepository.save(NewUser);

    return ResponseEntity.ok(new SignupResponseDto(username));
  }

  public ResponseEntity<String> deleteUser(UserDetailsImpl userDetails) {
    String username = userDetails.getUsername();

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    userRepository.delete(user);

    return ResponseEntity.ok(username + " user 삭제 성공");
  }

}
