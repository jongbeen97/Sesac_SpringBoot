package com.example.mybatis.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter // 저장된 값을 꺼내볼 수 있다.
public enum ErrorCode {
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"C001","입력 형태 올바르지 않음"), // 상수 값 처럼 쓰고 싶을때는 이름만 준다 (사실은 숫자0인데 ) 값으로 주지 않고 텍스트로 써서 상수화 시켜주는 것. 스프링 시큐리티는 ENUM가지고 ROLL 처리 함. 전체 3개 데이터를 관리한다는 것
  // 상태코드 ,  
  POST_NOT_FOUND(HttpStatus.NOT_FOUND,"P001","일치하는 게시글이 존재하지 않습니다."),
  INTERNAL_SERVER_ERRER(HttpStatus.INTERNAL_SERVER_ERROR,
    "S001","서버 내부에 예기치 않은 오류가 발생했습니다.");

  // 코드 3개를 저장할 필드 처리 
  private final HttpStatus status;
  private final String code;
  private final String message;

  // 생성자 생성 
  ErrorCode(HttpStatus status,String code,String message){
    this.status = status;
    this.code = code;
    this.message = message;
  }
  // final 빌드 생성할 때는 RequiredArgsConstructor
  // CustomException의 에러코드를 써야 한다. 

}
