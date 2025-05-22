package com.ssafy.pjtFinal.model.dao;

import com.ssafy.pjtFinal.model.dto.Follow;
import com.ssafy.pjtFinal.model.dto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowDao {

    // 팔로우하기
    void insertFollow(Follow follow);

    // 내가 팔로우 한 목록 조회
    List<User> followingList(String userId);

    // 나를 팔로우 한 목록 조회
    List<User> followerList(String userId);

    // 팔로잉 수 조회
    int followingCnt(String userId);

    // 팔로워 수 조회
    int followerCnt(String userUd);

    // 팔로잉 수 업데이트
    void updateFollowingCnt(@Param("userId") String userId, @Param("change") int change);

    // 팔로워 수 업데이트
    void updateFollowerCnt(@Param("userId") String userId, @Param("change") int change);

    // 내가 팔로우 한 유저인지 확인
    int checkFollowed(@Param("followerId") String followerId, @Param("followeeId") String followeeId);

    // 팔로우 취소
    void deleteFollow(@Param("follower") String follower, @Param("followee") String followee);
}
