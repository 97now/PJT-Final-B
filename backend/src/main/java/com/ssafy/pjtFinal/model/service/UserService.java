package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dto.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

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
    // 비밀번호 찾기
    Boolean findPw(FindPwRequest request);
    // 비밀번호 재설정
    void resetPw(ResetPwRequest request);
    // 모든 유저 리스트
    List<User> getUserAll(String userId);

    // 유저 수정
    void userModify(User user);
    // 유저 삭제
    void userRemove(String userId);

    // 비밀번호 인증
    boolean verifyPassword(LoginRequest request);
}
