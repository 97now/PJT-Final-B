package com.ssafy.pjtFinal.model.dao;

import com.ssafy.pjtFinal.model.dto.Video;
import com.ssafy.pjtFinal.model.dto.VideoLike;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoLikeDao {
    // 좋아요 하기
    void insertVideoLike(VideoLike videoLike);

    // 내가 좋아요 한 동영상 목록
    List<Video> selectLikedVideoList(String userId);

    // 내가 팔로우 한 사람이 좋아요 한 동영상 목록
    List<Video> selectLikedVideoListByFollowingList(String userId);

    // 내가 좋아요 한 영상인지 확인
    Integer checkLikedVideo(@Param("userId") String userId, @Param("videoId") long videoId);

    // 영상 좋아요 개수 세기
    int likeCountByVideoId(@Param("videoId") long videoId);

    // 좋아요 수 업데이트
    void updateLikeCountByVideoId(@Param("videoId") long videoId, @Param("change") int change);

    // 좋아요 취소
    void deleteVideoLike(@Param("userId") String userId, @Param("videoId") long videoId);
}
