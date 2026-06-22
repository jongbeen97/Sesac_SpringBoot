package com.example.mybatis.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor // 기본생성자 
@AllArgsConstructor // 필드 수정 
@Builder
@Getter
// @Data 실무에서 사용하면 안된다  
// tostring 은 출력할 필요 없으니 안해도 된다. 
public class User {
  private Long id;
  private String email;
  private String nickname;
  private LocalDateTime createdAt;
}
