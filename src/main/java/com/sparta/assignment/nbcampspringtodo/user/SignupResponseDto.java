package com.sparta.assignment.nbcampspringtodo.user;


import lombok.Getter;

@Getter
public class SignupResponseDto {

  private final String username;

  SignupResponseDto(User user) {
    this.username = user.getUsername();
  }

}
