package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dao.VideoLikeDao;
import com.ssafy.pjtFinal.model.dto.Video;
import com.ssafy.pjtFinal.model.dto.VideoLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoLikeServiceImpl implements VideoLikeService {

    // DI
    private VideoLikeDao videoLikeDao;
    @Autowired
    public VideoLikeServiceImpl(VideoLikeDao videoLikeDao) {
        this.videoLikeDao = videoLikeDao;
    }

    // 좋아요 누르기
    @Override
    @Transactional
    public void videoLikeAdd(VideoLike videoLike) {
        videoLikeDao.insertVideoLike(videoLike);
        videoLikeDao.updateLikeCountByVideoId(videoLike.getVideoId(), 1);
    }

    // 내가 좋아요 누른 영상들 가져오기
    @Override
    public List<Video> getLikedVideoList(String userId) {
        return videoLikeDao.selectLikedVideoList(userId);
    }

    // 내가 팔로우 하는 사람들이 좋아요 누른 영상 가져오기
    @Override
    public List<Video> getLikedVideoListByFollowingList(String userId) {
        return videoLikeDao.selectLikedVideoListByFollowingList(userId);
    }

    // 좋아요 된 영상인지 확인하기
    @Override
    public boolean checkLikedVideo(String userId, long videoId) {
        return videoLikeDao.checkLikedVideo(userId, videoId) == 1;
    }

    // 영상 좋아요 개수 세기
    @Override
    public long likeCountByVideoId(long videoId) {
        return videoLikeDao.likeCountByVideoId(videoId);
    }

    // 좋아요 취소
    @Override
    @Transactional
    public void deleteVideoLike(String userId, long videoId) {
        videoLikeDao.deleteVideoLike(userId, videoId);
        videoLikeDao.updateLikeCountByVideoId(videoId, -1);
    }
}
