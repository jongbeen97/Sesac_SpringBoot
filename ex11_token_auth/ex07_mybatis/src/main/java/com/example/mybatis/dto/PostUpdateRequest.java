package com.example.mybatis.dto;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(

/*UPDATE 요청
PUT /api/posts/123
  즉, userId는 경로 변수로 전달되기 때문에 PostUpdateRequest에서는 제외합니다.*/
  @NotBlank(message = "게시글 제목은 필수 항목입니다.")
  String title,
  
  @NotBlank(message = "게시글 내용은 필수 항목입니다.")
  String content)
 {

}
