package com.sparta.assignment.nbcampspringtodo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignupResponseDto {

  @Schema(defaultValue = "username")
  private final String username;


  SignupResponseDto(User user) {
    this.username = user.getUsername();
  }

}
