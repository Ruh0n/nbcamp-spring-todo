package com.sparta.assignment.nbcampspringtodo.user;

import com.sparta.assignment.nbcampspringtodo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/user/signup")
  public ResponseEntity<SignupResponseDto> signUp(
      @Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult
  ) {
    log.info("username: " + requestDto.getUsername());
    log.info("password: " + requestDto.getPassword());
    // Validation 예외처리
    if (bindingResult.hasErrors()) {
      StringBuilder errors = new StringBuilder();
      for (FieldError e : bindingResult.getFieldErrors()) {
        log.error("Field: " + e.getField() + ": " + e.getDefaultMessage());
        errors.append(e.getDefaultMessage()).append(" ");
      }

      return ResponseEntity.badRequest()
          .body(new SignupResponseDto(requestDto.getUsername(), errors.toString()));
    }

    return userService.signup(requestDto);
  }

  @DeleteMapping("/user")
  public ResponseEntity<String> deleteUser(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return userService.deleteUser(userDetails);
  }


}
