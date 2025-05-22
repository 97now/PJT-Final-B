package com.ssafy.pjtFinal.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @NotBlank
    private String userId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{8,20}$")
    private String userPw;

    @NotEmpty
    private String userNickName;

    @Email
    private String userEmail;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 000-0000-0000 형식이어야 합니다")
    private String userPhone;

    private LocalDateTime createdAt;

    private int followerCnt;
    private int followingCnt;

}
