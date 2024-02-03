package com.sparta.assignment.nbcampspringtodo.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request
  ) {
    StringBuilder errors = new StringBuilder();
    ex.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(" "));
    errors.setLength(errors.length() - 1);

    ResponseDto<?> responseDto = ResponseDto.builder()
        .httpStatus(HttpStatus.BAD_REQUEST)
        .message(errors.toString())
        .uri(((ServletWebRequest) request).getRequest().getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
  }

  @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
  protected ResponseEntity<ResponseDto<?>> handleIllegalArgumentException(
      RuntimeException ex, HttpServletRequest request
  ) {
    ResponseDto<?> responseDto = ResponseDto.builder()
        .httpStatus(HttpStatus.BAD_REQUEST)
        .message(ex.getMessage())
        .uri(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
  }

}
