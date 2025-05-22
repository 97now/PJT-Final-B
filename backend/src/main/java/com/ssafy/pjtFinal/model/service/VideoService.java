package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dto.SearchCondition;
import com.ssafy.pjtFinal.model.dto.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    // 비디오 추가
    void videoAdd(Video video, MultipartFile file);

    // 비디오 전체 반환
    List<Video> getVideoAll();

    // 비디오 하나 반환
    Video getVideo(long videoId);

    // 비디오 검색
    List<Video> searchVideo(SearchCondition condition);

    // 비디오 수정
    void videoModify(Video video, MultipartFile file);

    // 비디오 삭제
    void videoRemove(long videoID);

    // 비디오 조회수 증가
    void videoUpdateViewCnt(long videoId);
}
