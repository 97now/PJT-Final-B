package com.ssafy.pjtFinal.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {
    private long reviewId;

    @NotNull(message = "리뷰를 등록할 대상을 선택해주세요")
    private int videoId;

    @NotBlank(message = "작성자 아이디는 필수입니다")
    private String reviewUserId;

    @NotBlank(message = "작성자 닉네임은 필수입니다")
    private String reviewUserNickName;

    @NotBlank(message = "리뷰 내용을 입력하세요")
    private String reviewContent;

    private LocalDateTime reviewRegistDate;
}
