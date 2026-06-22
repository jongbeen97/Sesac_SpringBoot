package com.example.mybatis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
  @NotNull(message = "작성자의 아이디는 필수 항목 입니다.")
  Long userId,
  
  @NotBlank(message = "게시글 제목은 필수 항목입니다.")
  String title,
  
  String content
) {

}
