package com.FitInfo.FitInfo.global.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  EXIST_EMAIL(CONFLICT, "존재하는 EMAIL 입니다."),

  EXIST_USERNAME(CONFLICT, "존재하는 아이디 입니다.");

  private final HttpStatus status;

  private final String message;
}
