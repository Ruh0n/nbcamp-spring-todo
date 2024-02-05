package com.sparta.assignment.nbcampspringtodo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

  @Schema(defaultValue = "username")
  @NotBlank(message = "username은 공백일 수 없음")
  @Pattern(regexp = "^(?!.+ ).+$", message = "username은 공백을 포함하면 안됨")
  @Pattern(regexp = "^(?=.*\\d).+$", message = "username은 숫자를 포함해야 함")
  @Pattern(regexp = "^(?=.*[a-z]).+$", message = "username은 소문자를 포함해야 함")
  @Size(min = 4, max = 10, message = "username은 4자 이상 10자 이하만 가능")
  private String username;

  @Schema(defaultValue = "password")
  @NotBlank(message = "password는 공백일 수 없음")
  @Pattern(regexp = "^(?!.+ ).+$", message = "paswword는 공백을 포함하면 안됨")
  @Pattern(regexp = "^(?=.*\\d).+$", message = "paswword는 숫자를 포함해야 함")
  @Pattern(regexp = "^(?=.*[a-z]).+$", message = "paswword는 소문자를 포함해야 함")
  @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "paswword는 소문자를 포함해야 함")
  @Size(min = 8, max = 15, message = "password는 8자 이상 15자 이하만 가능")
  private String password;

}
