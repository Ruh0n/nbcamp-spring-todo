package com.sparta.assignment.nbcampspringtodo.security.controller;

import com.sparta.assignment.nbcampspringtodo.security.dto.SignupRequestDto;
import com.sparta.assignment.nbcampspringtodo.security.dto.SignupResponseDto;
import com.sparta.assignment.nbcampspringtodo.security.dto.UserInfoDto;
import com.sparta.assignment.nbcampspringtodo.security.security.UserDetailsImpl;
import com.sparta.assignment.nbcampspringtodo.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

      return ResponseEntity
          .badRequest()
          .body(new SignupResponseDto(requestDto.getUsername(), errors.toString()));
    }

    return userService.signup(requestDto);
  }

  @GetMapping("/user-info")
  @ResponseBody
  public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    String username = userDetails.user().getUsername();

    return new UserInfoDto(username);
  }

}
