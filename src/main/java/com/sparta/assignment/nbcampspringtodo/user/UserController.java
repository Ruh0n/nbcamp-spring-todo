package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.common.ResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "Operations about users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/v1")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Sign up")
  @PostMapping("/users/signup")
  public ResponseEntity<ResponseDto<SignupResponseDto>> signUp(
      @Valid @RequestBody SignupRequestDto requestDto
  ) {
    return userService.signup(requestDto);
  }

  @Operation(summary = "Login")
  @PostMapping("/users/login")
  public void login(
      @RequestBody LoginRequestDto loginRequestDto
  ) {
    System.out.println("This method does nothing" + loginRequestDto);
  }

  @Operation(summary = "Delete a User")
  @DeleteMapping("/users")
  public ResponseEntity<ResponseDto<String>> deleteUser(
      @RequestBody DeleteUserRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return userService.deleteUser(requestDto, userDetails.getUsername());
  }

}

