package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dao.UserDao;
import com.ssafy.pjtFinal.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final FollowService followService;
    // 의존성 주입
    UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, FollowService followService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.followService = followService;
    }

    // 회원가입
    @Override
    @Transactional
    public void userAdd(User user) {
        if(userDao.userSelectOne(user.getUserId()) != null)
                throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        String encodedPw = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(encodedPw);
        userDao.userInsert(user);
    }

    // 로그인
    @Override
    public User userLogin(LoginRequest request) {
        System.out.println("[UserServiceImpl] 로그인 함수 호출");
        User user = getUserOne(request.getUserId());
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        if(!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return user;
    }

    // 유저 한 명 선택
    @Override
    public User getUserOne(String userId) {
        return userDao.userSelectOne(userId);
    }

    // 아이디 찾기
    @Override
    public String findId(FindIdRequest request) {
        return userDao.findId(request);
    }

    // 비밀번호 찾기
    @Override
    public Boolean findPw(FindPwRequest request) {
        Integer result = userDao.findPw(request);
        return result != null && result == 1;
    }

    // 비밀번호 재설정
    @Override
    public void resetPw(ResetPwRequest request) {
        User user = getUserOne(request.getUserId());
        if(passwordEncoder.matches(request.getNewPw(), user.getUserPw())) {
            throw new CustomException(ErrorCode.SAME_PASSWORD);
        }
        String encodedPw = passwordEncoder.encode(request.getNewPw());
        request.setNewPw(encodedPw);
        userDao.resetPw(request);
    }

    // 모든 유저 리스트
    @Override
    public List<User> getUserAll(String userId){
        List<User> userList = userDao.userSelectAll();

        for(User u : userList) {
            u.setCheckFollowed(followService.checkFollowed(userId, u.getUserId()));
        }
        return userDao.userSelectAll();
    }

    // 회원 정보 수정
    @Override
    @Transactional
    public void userModify(User user) {
        String encodedPw = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(encodedPw);
        userDao.userUpdate(user);
    }

    // 회원 정보 삭제
    @Override
    @Transactional
    public void userRemove(String userId) {
        userDao.userDelete(userId);
    }

    // 비밀번호 인증
    @Override
    public boolean verifyPassword(LoginRequest request) {
        User user = userDao.userSelectOne(request.getUserId());
        return passwordEncoder.matches(request.getUserPw(), user.getUserPw());
    }
}
