package com.ssafy.pjtFinal.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Follow {
    private long id; // 해당 팔로우 고유키
    private String followee;    // 팔로우 누른 유저아이디
    private String follower;    // 팔로우 대상 유저 아이디
    private LocalDateTime followedAt; // 팔로우한 시간
}
