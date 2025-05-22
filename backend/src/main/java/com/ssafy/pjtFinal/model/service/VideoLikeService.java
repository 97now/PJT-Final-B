package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dto.Video;
import com.ssafy.pjtFinal.model.dto.VideoLike;

import java.util.List;

public interface VideoLikeService {
    // 좋아요 하기
    void videoLikeAdd(VideoLike videoLike);

    // 내가 좋아요 한 동영상 목록
    List<Video> getLikedVideoList(String userId);

    // 내가 팔로우 한 사람이 좋아요 한 동영상 목록
    List<Video> getLikedVideoListByFollowingList(String userId);

    // 내가 좋아요 한 영상인지 확인
    boolean checkLikedVideo(String userId, long videoId);

    // 영상 좋아요 개수 세기
    long likeCountByVideoId(long videoId);

    // 좋아요 취소
    void deleteVideoLike(String userId, long videoId);
}
