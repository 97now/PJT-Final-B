package com.ssafy.pjtFinal.model.dao;

import com.ssafy.pjtFinal.model.dto.User;

import java.util.List;

public interface UserDao {
    void userInsert(User user);
    User userSelectOne(String userId);
    List<User> userSelectAll();
    void userUpdate(User user);
    void userDelete(String userId);
}
