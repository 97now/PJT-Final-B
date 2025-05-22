package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.SearchCondition;
import com.ssafy.pjtFinal.model.dto.Video;
import com.ssafy.pjtFinal.model.service.VideoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    // 의존성 주입
    private final VideoService videoService;

    // 생성자 주입
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // 비디오 등록
    @PostMapping
    public ResponseEntity<Video> addVideo(@Valid @ModelAttribute Video video, @RequestParam("file") MultipartFile file) {
        videoService.videoAdd(video, file);
        URI location = URI.create("/api/video/" + video.getVideoId());
        return ResponseEntity.created(location).body(video);
    }

    // 비디오 전체 조회 or 검색 결과 조회
    @GetMapping
    public ResponseEntity<List<Video>> searchVideo(@ModelAttribute SearchCondition condition) {
        List<Video> videoList = videoService.searchVideo(condition);
        return ResponseEntity.status(HttpStatus.OK).body(videoList);
    }

    // 비디오 하나 조회
    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideo(@PathVariable int videoId, HttpSession session) {
        Video video = videoService.getVideo(videoId);
        if (video == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        // 세션당 한 번만 조회수 올려줄게
        @SuppressWarnings("unchecked")
        Set<Integer> viewedVideoIds = (Set<Integer>) session.getAttribute("viewedVideoIds");
        if (viewedVideoIds == null) {
            viewedVideoIds = new HashSet<>();
        }
        if (!viewedVideoIds.contains(videoId)) {
            videoService.videoUpdateViewCnt(videoId);
            video.setVideoViewCnt(video.getVideoViewCnt() + 1);
            viewedVideoIds.add(videoId);
            session.setAttribute("viewedVideoIds", viewedVideoIds);
        }
        return ResponseEntity.ok(video);
    }

    // 비디오 수정
    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(@PathVariable int videoId, @Valid @ModelAttribute Video video, @RequestParam("file") MultipartFile file) {
        Video oldVideo = videoService.getVideo(videoId);
        if (oldVideo == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        video.setVideoId(videoId);
        videoService.videoModify(video, file);

        return ResponseEntity.ok(video);
    }

    // 비디오 삭제
    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable int videoId) {
        Video targetVideo = videoService.getVideo(videoId);
        if (targetVideo == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        videoService.videoRemove(videoId);

        return ResponseEntity.noContent().build();
    }

}
