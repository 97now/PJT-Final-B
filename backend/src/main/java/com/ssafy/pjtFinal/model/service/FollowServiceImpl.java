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
    public void followAdd(String followerId, String followeeId) {
        Follow follow = new Follow();

        follow.setFollower(followerId);
        follow.setFollowee(followeeId);
        followDao.insertFollow(follow);

        followDao.updateFollowerCnt(followeeId, 1);
        followDao.updateFollowingCnt(followerId, 1);
    }

    // 내가 팔로우하는 유저 리스트
    @Override
    public List<User> getFollowingList(String userId) {

        List<User> list = followDao.followingList(userId);
        for (User u : list) {
            u.setCheckFollowed(true);
        }
        return list;
    }

    // 나를 팔로우하는 유저 리스트
    @Override
    public List<User> getFollowerList(String userId) {
        List<User> list = followDao.followerList(userId);
        for(User u : list) {
            u.setCheckFollowed(checkFollowed(userId, u.getUserId()));
        }
        return list;
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

    // 팔로우여부
    @Override
    public boolean checkFollowed(String followerId, String followeeId) {
        Integer result = followDao.checkFollowed(followerId, followeeId);
        return result != null && result == 1;
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
