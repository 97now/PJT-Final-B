package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dto.FindIdRequest;
import com.ssafy.pjtFinal.model.dto.LoginRequest;
import com.ssafy.pjtFinal.model.dto.User;

import java.util.List;

public interface UserService {
    // 유저 등록
    void userAdd(User user);
    // 로그인
    User userLogin(LoginRequest request);
    // 유저 하나
    User getUserOne(String userId);
    // 아이디 찾기
    String findId(FindIdRequest request);
    // 유저 모두
    List<User> getUserAll();
    // 유저 수정
    void userModify(User user);
    // 유저 삭제
    void userRemove(String userId);
}
