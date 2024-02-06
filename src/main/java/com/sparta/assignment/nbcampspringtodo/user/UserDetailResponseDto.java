package com.sparta.assignment.nbcampspringtodo.user;

import lombok.Getter;

@Getter
public class UserDetailResponseDto {

  private final Long id;
  private final String username;

  public UserDetailResponseDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }

}
