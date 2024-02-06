package com.sparta.assignment.nbcampspringtodo.feature.user;

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

@Tag(name = "1. User API", description = "Operations about users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/v1")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Sign up")
  @PostMapping("/users/signup")
  public ResponseEntity<ResponseDto<UserDetailResponseDto>> signUp(
      @Valid @RequestBody SignupRequestDto requestDto
  ) {
    UserDetailResponseDto data = userService.signup(requestDto);
    return ResponseEntity.ok(ResponseDto.of(data, "user 등록 성공"));
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
    String data = userService.deleteUser(requestDto, userDetails.getUsername());
    return ResponseEntity.ok(ResponseDto.of(data, "user 삭제 성공"));
  }

}

