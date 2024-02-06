package com.sparta.assignment.nbcampspringtodo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserDetailResponseDto {

  private final Long id;
  @Schema(description = "username")
  private final String username;

  public UserDetailResponseDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }

}
