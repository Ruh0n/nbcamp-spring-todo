package com.sparta.assignment.nbcampspringtodo.feature.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

  @Schema(defaultValue = "username")
  private String username;

  @Schema(defaultValue = "password")
  private String password;

}
