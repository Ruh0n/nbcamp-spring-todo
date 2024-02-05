package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.common.Verifier;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final Verifier verifier;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public ResponseEntity<ResponseDto<SignupResponseDto>> signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("username 중복");
    }

    return ResponseEntity.ok(ResponseDto.<SignupResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("user 등록 성공")
        .data(new SignupResponseDto(userRepository.save(new User(username, password))))
        .build());
  }

  @Transactional
  public ResponseEntity<ResponseDto<String>> deleteUser(
      DeleteUserRequestDto requestDto, String username
  ) {
    User user = verifier.verifyUserWithPassword(username, requestDto.getPassword());

    userRepository.delete(user);

    return ResponseEntity.ok(ResponseDto.<String>builder()
        .httpStatus(HttpStatus.OK)
        .message("user 삭제 성공")
        .build());
  }

}
