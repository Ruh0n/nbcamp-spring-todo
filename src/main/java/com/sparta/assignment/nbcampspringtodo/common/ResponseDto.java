package com.sparta.assignment.nbcampspringtodo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

  private HttpStatus httpStatus;

  private String message;

  private String uri;

  private T data;


  public String getHttpStatus() {
    return httpStatus.toString();
  }

}
