package com.example.valid.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record MemberCreateRequest(

    @NotBlank(message = "이름은 필수 입력 항목입니다.") @Size(min = 2, max = 10, message = "이름은 최소 2글자 , 최대 10글자 입력해야 합니다 ") String username,

    @NotBlank(message = "이메일은 필수 입력 항목입니다.") @Email(message = "올바른 이메일 형식이 아닙니다") String email) {

}
