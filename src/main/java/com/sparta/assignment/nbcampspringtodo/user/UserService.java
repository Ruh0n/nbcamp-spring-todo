package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.common.Verifier;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
  public SignupResponseDto signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    Optional<User> existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("username 중복");
    }

    return new SignupResponseDto(userRepository.save(new User(username, password)));
  }

  @Transactional
  public String deleteUser(
      DeleteUserRequestDto requestDto, String username
  ) {
    User user = verifier.verifyUserWithPassword(username, requestDto.getPassword());

    userRepository.delete(user);

    return "username: " + username;
  }

}
