package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user/v1")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/users/signup")
  public ResponseEntity<ResponseDto<SignupResponseDto>> signUp(
      @Valid @RequestBody SignupRequestDto requestDto
  ) {
    return userService.signup(requestDto);
  }

  @DeleteMapping("/users")
  public ResponseEntity<ResponseDto<String>> deleteUser(
      @Valid @RequestBody DeleteUserRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return userService.deleteUser(requestDto, userDetails.getUsername());
  }

}
