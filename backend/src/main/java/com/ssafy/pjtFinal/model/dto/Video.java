package com.ssafy.pjtFinal.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Video {
	private long videoId;

	@NotBlank(message = "제목은 필수 입력값입니다")
	private String videoTitle;
	
	@NotBlank(message = "운동 부위를 입력하세요")
	private String videoPart;

	@NotBlank(message = "영상 링크를 입력하세요")
	private String videoUrl;

	private int videoViewCnt;
	private int videoLikeCnt;

	@NotBlank(message = "채널명을 입력하세요")
	private String videoChannelName;

	private String orgImgName;
	private String unqImgName;
}
