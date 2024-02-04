package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import jakarta.validation.Valid;
import java.util.Objects;
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

    ResponseDto<SignupResponseDto> responseDto = ResponseDto.<SignupResponseDto>builder()
        .httpStatus(HttpStatus.OK)
        .message("user 등록 성공")
        .data(new SignupResponseDto(userRepository.save(new User(username, password))))
        .build();

    return ResponseEntity.ok(responseDto);
  }

  public ResponseEntity<ResponseDto<String>> deleteUser(
      @Valid DeleteUserRequestDto requestDto, String username
  ) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NullPointerException("user를 찾을 수 없음"));

    if (!Objects.equals(passwordEncoder.encode(requestDto.getPassword()), user.getPassword())) {
      throw new IllegalArgumentException("password가 일치하지 않음");
    }

    userRepository.delete(user);

    ResponseDto<String> responseDto = ResponseDto.<String>builder()
        .httpStatus(HttpStatus.OK)
        .message("user 삭제 성공")
        .build();

    return ResponseEntity.ok(responseDto);
  }

}
