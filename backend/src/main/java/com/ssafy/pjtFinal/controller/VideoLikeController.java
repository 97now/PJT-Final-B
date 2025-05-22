package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.User;
import com.ssafy.pjtFinal.model.dto.Video;
import com.ssafy.pjtFinal.model.dto.VideoLike;
import com.ssafy.pjtFinal.model.service.FollowService;
import com.ssafy.pjtFinal.model.service.UserService;
import com.ssafy.pjtFinal.model.service.VideoLikeService;
import com.ssafy.pjtFinal.model.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video-like")
public class VideoLikeController {

    // DI
    private VideoLikeService videoLikeService;
    private VideoService videoService;
    private UserService userService;
    private FollowService followService;

    @Autowired
    public VideoLikeController(VideoLikeService videoLikeService, VideoService videoService, UserService userService, FollowService followService) {
        this.videoLikeService = videoLikeService;
        this.videoService = videoService;
        this.userService = userService;
        this.followService = followService;
    }

    // 좋아요 누르기
    @PostMapping
    public ResponseEntity<Void> videoLikeAdd(@RequestBody VideoLike videoLike) {
        Video video = videoService.getVideo(videoLike.getVideoId());

        if (videoLikeService.checkLikedVideo(videoLike.getUserId(), videoLike.getVideoId()))
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        if (video == null)
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        if (userService.getUserOne(videoLike.getUserId()) == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        videoLikeService.videoLikeAdd(videoLike);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 내가 좋아요 누른 영상들
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Video>> videoList(@PathVariable String userId) {
        List<Video> videoList = videoLikeService.getLikedVideoList(userId);
        User user = userService.getUserOne(userId);

        if (user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        if (videoList.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(videoList);
    }

    // 내가 팔로우 하는 사람들이 좋아요 누른 영상들
    @GetMapping("/user/{userId}/following")
    public ResponseEntity<List<Video>> videoListByFollowingList(@PathVariable String userId) {
        List<Video> videoList = videoLikeService.getLikedVideoListByFollowingList(userId);
        List<User> followingList = followService.getFollowingList(userId);

        if (followingList.isEmpty() || videoList.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(videoList);
    }

    // 좋아요 된 영상인지 확인하기
    @GetMapping("/user/{userId}/video/{videoId}/check")
    public ResponseEntity<Boolean> checkLikedVideo(@PathVariable String userId, @PathVariable long videoId) {
        Video video = videoService.getVideo(videoId);
        User user = userService.getUserOne(userId);

        if (user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        if (video == null)
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);

        return ResponseEntity.ok(videoLikeService.checkLikedVideo(userId,videoId));
    }

    // 영상 좋아요 개수 세기
    @GetMapping("/video/{videoId}/count")
    public ResponseEntity<Long> countLikes(@PathVariable long videoId) {
        Video video = videoService.getVideo(videoId);

        if (video == null)
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);

        return ResponseEntity.ok(videoLikeService.likeCountByVideoId(videoId));
    }

    // 좋아요 취소
    @DeleteMapping("/user/{userId}/video/{videoId}")
    public ResponseEntity<Void> deleteVideoLike(@PathVariable String userId, @PathVariable long videoId) {
        Video video = videoService.getVideo(videoId);
        User user = userService.getUserOne(userId);

        if(user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        if(video == null)
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);

        videoLikeService.deleteVideoLike(userId, videoId);
        return ResponseEntity.ok().build();
    }
}
