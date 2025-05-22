package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dao.FollowDao;
import com.ssafy.pjtFinal.model.dto.Follow;
import com.ssafy.pjtFinal.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    // DI
    private FollowDao followDao;
    @Autowired
    public FollowServiceImpl(FollowDao followDao) {
        this.followDao = followDao;
    }

    // 팔로우 추가
    @Override
    @Transactional
    public void followAdd(Follow follow) {
        followDao.insertFollow(follow);
        followDao.updateFollowerCnt(follow.getFollowee(), 1);
        followDao.updateFollowingCnt(follow.getFollower(), -1);
    }

    // 내가 팔로우하는 유저 리스트
    @Override
    public List<User> getFollowingList(String userId) {
        return followDao.followingList(userId);
    }

    // 나를 팔로우하는 유저 리스트
    @Override
    public List<User> getFollowerList(String userId) {
        return followDao.followerList(userId);
    }

    // 팔로잉 수
    @Override
    public int followingCnt(String userId) {
        return followDao.followingCnt(userId);
    }

    // 팔로워 수
    @Override
    public int followerCnt(String userId) {
        return followDao.followerCnt(userId);
    }

    @Override
    public boolean checkFollowed(String followerId, String followeeId) {
        return followDao.checkFollowed(followerId, followeeId) == 1;
    }

    // 언팔로우
    @Override
    @Transactional
    public void followRemove(String follower, String followee) {
        followDao.deleteFollow(follower, followee);
        followDao.updateFollowerCnt(followee, -1);
        followDao.updateFollowingCnt(follower, -1);
    }
}
