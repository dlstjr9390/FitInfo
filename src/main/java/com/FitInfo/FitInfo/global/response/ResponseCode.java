package com.FitInfo.FitInfo.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

  OK(200, "요청 성공");

  private final int HttpStatus;

  private final String message;

}
