package com.example.valid.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record MemberDTO(
  Long id,
  String username,
  String email,
  LocalDateTime createdAt
) {

}
