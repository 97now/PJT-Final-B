package com.ssafy.pjtFinal.model.dao;

import com.ssafy.pjtFinal.model.dto.FindIdRequest;
import com.ssafy.pjtFinal.model.dto.FindPwRequest;
import com.ssafy.pjtFinal.model.dto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    void userInsert(User user);
    User userSelectOne(String userId);
    String findId(FindIdRequest request);
    List<User> userSelectAll();
    void userUpdate(User user);
    void userDelete(String userId);
    Integer findPw(FindPwRequest request);
}
