package com.ssafy.pjtFinal.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "아이디를 입력하세요")
    private String userId;
    
    @NotBlank(message = "비밀번호를 입력하세요")
    private String userPw;
}
