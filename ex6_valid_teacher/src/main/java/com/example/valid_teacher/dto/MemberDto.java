package com.example.valid_teacher.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record MemberDto(
    Long id,
    String username,
    String email,
    LocalDateTime createdAt
) { }