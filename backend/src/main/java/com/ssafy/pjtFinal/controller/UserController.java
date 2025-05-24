package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.LoginRequest;
import com.ssafy.pjtFinal.model.dto.User;
import com.ssafy.pjtFinal.model.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 등록
    @PostMapping
    public ResponseEntity<User> registUser(@Valid @RequestBody User user) {
        userService.userAdd(user);
        URI location = URI.create("/api/user/" + user.getUserId());
        return ResponseEntity.created(location).body(user);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = userService.userLogin(request);

        if (user == null)
            throw new CustomException(ErrorCode.VALIDATION_FAILED);

        System.out.println(user);
        session.setAttribute("loginUser", user);
        return ResponseEntity.ok(user);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        if(user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        session.removeAttribute("loginUser");
        return ResponseEntity.noContent().build();
    }

    // 유저 조회 (단일)
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserOne(userId);

        if(user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return ResponseEntity.ok(user);
    }

    // 유저 조회 (전체)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUserAll();

        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    // 유저 수정
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @Valid @RequestBody User user) {
        User requestUser = userService.getUserOne(userId);

        if(requestUser == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        user.setUserId(userId);
        userService.userModify(user);
        return ResponseEntity.ok(user);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        User user = userService.getUserOne(userId);

        if(user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        userService.userRemove(userId);
        return ResponseEntity.noContent().build();
    }
}
