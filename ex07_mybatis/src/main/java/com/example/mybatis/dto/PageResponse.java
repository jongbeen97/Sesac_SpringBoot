package com.example.mybatis.dto;

import java.util.List;

/*
페이징 처리할 때는 이 부분을 사용합니다. 
전체 갯수를 반환을 할수 있다. 
페이지 넘기는 것은 거꾸로 구현 
*/
public record PageResponse<T>(
  List<T> contents, // 제네릭을 활용하여 user와 post를 저장
  int page,
  int size,
  int totalPages,
  long totalElements,
  String sort ) {
  

}
