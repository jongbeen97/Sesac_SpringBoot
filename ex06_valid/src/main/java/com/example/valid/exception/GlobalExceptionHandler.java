package com.example.valid.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // RestController에서 발생하는 모든 예외를 가로챈뒤 처리하는 클래스 
public class GlobalExceptionHandler {

  //@Valid 검증 실패시 발생하는 MethodArgumentNotValidException 예외 처리기
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE); // 400 에러 코드를 선택하여 넘기기 
    return new ResponseEntity<>(errorResponse, ErrorCode.INVALID_INPUT_VALUE.getStatus());
    
  }
  // Custom Exception 예외 처리기 
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
    ErrorCode errorCode = e.getErrorCode();
    ErrorResponse errorResponse = ErrorResponse.of(errorCode);
    return new ResponseEntity<>(errorResponse, errorCode.getStatus());
  }

  // 나머지 모든 예외 처리기 
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> hanedleException(Exception e){
    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(errorResponse,ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
  }

}
