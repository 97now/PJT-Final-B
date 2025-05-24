package com.ssafy.pjtFinal.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{8,20}$", message = "영어 소문자 또는 숫자로만 이루어진 8~20자여야 합니다.")
    private String userId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,20}$")
    private String userPw;

    @NotEmpty
    private String userNickName;

    @Email
    private String userEmail;

    @Pattern(regexp = "^010[0-9]{8}$", message = "전화번호는 010으로 시작하는 11자리 숫자여야 합니다")
    private String userPhone;

    private LocalDate userBirth;

    private LocalDateTime createdAt;

    private int followerCnt;
    private int followingCnt;

}
