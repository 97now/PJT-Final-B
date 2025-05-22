package com.ssafy.pjtFinal.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoWithLikeUser {
    // 비디오 정보
    private Video video;
    
    // 좋아요 누른 사람 정보
    private User user;

    // 좋아요 누른 시간
    private LocalDateTime likedAt;
}
