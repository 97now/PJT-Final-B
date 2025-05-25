package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.Follow;
import com.ssafy.pjtFinal.model.dto.User;
import com.ssafy.pjtFinal.model.service.FollowService;
import com.ssafy.pjtFinal.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    // DI
    private FollowService followService;
    private UserService userService;

    @Autowired
    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    // 팔로우하기
    @PostMapping("/{targetId}")
    public ResponseEntity<?> followAdd(@PathVariable String targetId, @AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        followService.followAdd(currentUserId, targetId);
        return ResponseEntity.ok("팔로우 완료");
    }

    // 내가 팔로우 한 목록 조회
    @GetMapping("/following")
    public ResponseEntity<List<User>> followingList(@AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        List<User> followingList = followService.getFollowingList(currentUserId);
        System.out.println("[FollowController] " + currentUserId + "의 팔로잉 리스트 : " + followingList);
        return followingList.isEmpty() ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(followingList);
    }

    // 유저를 팔로우 한 목록 조회
    @GetMapping("/follower")
    public ResponseEntity<List<User>> followerList(@AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        List<User> followerList = followService.getFollowerList(currentUserId);
        System.out.println("[FollowController] " + currentUserId + "의 팔로워 리스트 : " + followerList);
        return followerList.isEmpty() ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(followerList);
    }

    // 내가 팔로잉 하고 있는 사용자인지 체크
    @GetMapping("/{followeeId}/check")
    public ResponseEntity<Boolean> checkFollowed(@PathVariable String followeeId, @AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        boolean isFollowing = followService.checkFollowed(currentUserId, followeeId);

        return ResponseEntity.ok(isFollowing);
    }

    // 팔로우 삭제
    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteFollow(@PathVariable String targetId, @AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        followService.followRemove(currentUserId, targetId);
        return ResponseEntity.ok().build();
    }
}
