package com.ssafy.pjtFinal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindPwRequest {
    private String userId;
    private String userPhone;
}
