package com.example.valid.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

  // 400 Bad Request
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"C001", "올바르지 않는 입력값 입니다."),

  // 404 Not Found
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"M001","존재하지 않는 회원입니다."),

  // 409 Conflict
  DUPLICATE_EMAIL(HttpStatus.CONFLICT,"M002","이미 존재하는 이메일 입니다."),

  // 500 Internal Server Error 
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"S001","서버 내부 오류가 발생했습니다.");

  private HttpStatus status;
  private String code;
  private String message;

  ErrorCode(HttpStatus status, String code, String message){
    this.message = message;
    this.status = status;
    this.code = code;
  }

}
