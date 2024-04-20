package com.FitInfo.FitInfo.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  WRONG_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),

  DELETED_USER(BAD_REQUEST, "탈퇴 처리 된 계정입니다. 고객문의를 이용해 주십시오."),

  NEED_LOGIN(UNAUTHORIZED, "로그인이 필요합니다."),

  NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 아이디입니다."),

  EXIST_EMAIL(CONFLICT, "존재하는 EMAIL 입니다."),

  EXIST_USERNAME(CONFLICT, "존재하는 아이디 입니다."),

  YOUR_NOT_COME_IN(FORBIDDEN, "접근 권한이 없습니다.");

  private final HttpStatus status;

  private final String message;
}
