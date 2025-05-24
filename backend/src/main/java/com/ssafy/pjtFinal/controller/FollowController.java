package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.Follow;
import com.ssafy.pjtFinal.model.dto.User;
import com.ssafy.pjtFinal.model.service.FollowService;
import com.ssafy.pjtFinal.model.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<Void> followAdd(@RequestBody Follow follow) {
        User follower = userService.getUserOne(follow.getFollower());
        User followee = userService.getUserOne(follow.getFollowee());

        if (follower == null || followee == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        if(followService.checkFollowed(follow.getFollower(), follow.getFollowee()))
            throw new CustomException(ErrorCode.ALREADY_FOLLOWED);

        followService.followAdd(follow);
        return ResponseEntity.ok().build();
    }

    // 내가 팔로우 한 목록 조회
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<User>> followingList(@PathVariable String userId) {
        List<User> followingList = followService.getFollowingList(userId);
        User user = userService.getUserOne(userId);

        if (user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return followingList.isEmpty() ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(followingList);
    }

    // 유저를 팔로우 한 목록 조회
    @GetMapping("/{userId}/follower")
    public ResponseEntity<List<User>> followerList(@PathVariable String userId) {
        List<User> followerList = followService.getFollowerList(userId);
        User user = userService.getUserOne(userId);

        if (user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return followerList.isEmpty() ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(followerList);
    }

    // 내가 팔로잉 하고 있는 사용자인지 체크
    @GetMapping("/{followeeId}/check")
    public ResponseEntity<Boolean> checkFollowed(HttpSession session, @PathVariable String followeeId){
        User follower = (User) session.getAttribute("loginUser");
        User followee = userService.getUserOne(followeeId);

        if(follower == null || followee == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        String followerId = follower.getUserId();
        return ResponseEntity.ok(followService.checkFollowed(followerId, followeeId));
    }

    // 팔로우 삭제
    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteFollow(HttpSession session, @PathVariable String targetId) {
        User follower = (User) session.getAttribute("loginUser");
        User followee = userService.getUserOne(targetId);

        if(followee == null || follower == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        String userId = follower.getUserId();
        followService.followRemove(userId, targetId);
        return ResponseEntity.ok().build();
    }
}
