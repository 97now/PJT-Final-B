package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dto.Follow;
import com.ssafy.pjtFinal.model.dto.User;

import java.util.List;

public interface FollowService {
    // 팔로우하기
    void followAdd(Follow follow);

    // 내가 팔로우 한 목록 조회
    List<User> getFollowingList(String userId);

    // 나를 팔로우 한 목록 조회
    List<User> getFollowerList(String userId);

    // 팔로잉 수 조회
    int followingCnt(String userId);

    // 팔로워 수 조회
    int followerCnt(String userId);

    // 내가 이미 팔로잉 한 사람인지 확인
    boolean checkFollowed(String followerId, String followeeId);

    // 팔로우 취소
    void followRemove(String follower, String followee);
}
