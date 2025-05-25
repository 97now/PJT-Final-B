package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dao.UserDao;
import com.ssafy.pjtFinal.model.dto.FindIdRequest;
import com.ssafy.pjtFinal.model.dto.LoginRequest;
import com.ssafy.pjtFinal.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    // 의존성 주입
    UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Override
    @Transactional
    public void userAdd(User user) {
//        System.out.println("[DEBUG] 회원가입 메서드 진입");

        String encodedPw = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(encodedPw);

        if(userDao.userSelectOne(user.getUserId()) != null)
                throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        userDao.userInsert(user);
    }

    // 로그인
    @Override
    public User userLogin(LoginRequest request) {
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

    // 모든 유저 리스트
    @Override
    public List<User> getUserAll() {
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
}
