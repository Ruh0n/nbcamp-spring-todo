package com.sparta.assignment.nbcampspringtodo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

  @Schema(example = "200 OK")
  private HttpStatus httpStatus;

  @Schema(example = "SUCCESSFUL")
  private String message;

  @Schema(hidden = true)
  private String uri;

  private T data;


  public String getHttpStatus() {
    return httpStatus.toString();
  }

}
