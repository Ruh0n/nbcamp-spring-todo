package com.sparta.assignment.nbcampspringtodo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

  @NotBlank(message = "username은 공백일 수 없습니다.")
  @Size(min = 4, max = 10, message = "username은 4자 이상 10자 이하만 가능합니다.")
  @Pattern(regexp = "^(?!.+ ).+$", message = "username은 공백을 포함하면 안됩니다.")
  @Pattern(regexp = "^(?=.*\\d).+$", message = "username은 숫자를 포함해야 합니다.")
  @Pattern(regexp = "^(?=.*[a-z]).+$", message = "username은 소문자를 포함해야 합니다.")
  private String username;


  @NotBlank(message = "password는 공백일 수 없습니다.")
  @Pattern(regexp = "^(?!.+ ).+$", message = "paswword는 공백을 포함하면 안됩니다.")
  @Pattern(regexp = "^(?=.*\\d).+$", message = "paswword는 숫자를 포함해야 합니다.")
  @Pattern(regexp = "^(?=.*[a-z]).+$", message = "paswword는 소문자를 포함해야 합니다.")
  @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "paswword는 소문자를 포함해야 합니다.")
  @Size(min = 8, max = 15, message = "password는 8자 이상 15자 이하만 가능합니다.")
  private String password;

}
