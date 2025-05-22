package com.ssafy.pjtFinal.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoLike {
    private int id;

    @NotNull(message = "좋아요 대상 비디오를 찾을 수 없습니다")
    private int videoId;

    private LocalDateTime likedAt;

    @NotBlank(message = "유저 정보를 찾을 수 없습니다")
    private String userId;

    private String userNickname;
}
