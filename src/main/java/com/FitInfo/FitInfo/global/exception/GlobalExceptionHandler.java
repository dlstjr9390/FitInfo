package com.FitInfo.FitInfo.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BisException.class)
  public ResponseEntity<ExceptionResponseDto> bisExceptionHandler(BisException e){

    ExceptionResponseDto responseDto = new ExceptionResponseDto(e.getErrorCode());

    return ResponseEntity.status(responseDto.getStatus()).body(responseDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e
  ) {
    BindingResult bindingResult = e.getBindingResult();
    String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ExceptionResponseDto(HttpStatus.BAD_REQUEST, message)
    );
  }
}
