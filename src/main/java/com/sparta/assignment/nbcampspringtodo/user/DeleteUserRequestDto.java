package com.sparta.assignment.nbcampspringtodo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteUserRequestDto {

  @Schema(example = "password")
  @NotBlank(message = "password는 공백일 수 없습니다.")
  @Pattern(regexp = "^(?!.+ ).+$", message = "paswword는 공백을 포함하면 안됩니다.")
  @Pattern(regexp = "^(?=.*\\d).+$", message = "paswword는 숫자를 포함해야 합니다.")
  @Pattern(regexp = "^(?=.*[a-z]).+$", message = "paswword는 소문자를 포함해야 합니다.")
  @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "paswword는 소문자를 포함해야 합니다.")
  @Size(min = 8, max = 15, message = "password는 8자 이상 15자 이하만 가능합니다.")
  private String password;

}
