package com.FitInfo.FitInfo.global.exception;

import lombok.Getter;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResponseDto {

  private final HttpStatus status;

  private final String message;

  public ExceptionResponseDto(ErrorCode e) {
    this.status = e.getStatus();
    this.message = e.getMessage();
  }

  public ExceptionResponseDto(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
