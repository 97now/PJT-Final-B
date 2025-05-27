package com.ssafy.pjtFinal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String userId;
    private String profileImg;
}
