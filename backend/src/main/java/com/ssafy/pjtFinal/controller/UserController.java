package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.*;
import com.ssafy.pjtFinal.model.service.UserService;
import com.ssafy.pjtFinal.security.JwtUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("[UserController] 로그인 요청 들어옴");

        try {
//            User user = userService.userLogin(request);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserId(), request.getUserPw())
            );
            String token = jwtUtil.generateToken(request.getUserId());
            return ResponseEntity.ok(new LoginResponse(token, request.getUserId()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 틀림 😕");
        }
    }

    // 비밀번호 인증
    @PostMapping("/verifyPw")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody LoginRequest request) {
        boolean result = userService.verifyPassword(request);
//        System.out.println("[UserController] 비밀번호 인증 결과 = " + result);
        return ResponseEntity.ok(result);
    }

    // 유저 조회 (단일)
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
//        System.out.println("[UserController] 유저 조회 진입");
        User user = userService.getUserOne(userId);

        if(user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return ResponseEntity.ok(user);
    }

    // 유저 조회 (전체)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails) {
        List<User> users = userService.getUserAll(userDetails.getUsername());
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    // 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<?> findId(@RequestBody FindIdRequest request){
        System.out.println("[UserController] 아이디 찾기 호출 - " + request);
        String userId = userService.findId(request);
        if(userId == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return ResponseEntity.ok(userId);
    }

    // 비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody FindPwRequest request) {
        System.out.println("[UserController] 비밀번호 찾기 호출 - " + request.getUserId() + " & " + request.getUserPhone());
        Boolean isCorrect = userService.findPw(request);
        return ResponseEntity.ok(isCorrect);
    }

    // 비밀번호 재설정
    @PostMapping("/resetPw")
    public ResponseEntity<?> resetPw(@RequestBody ResetPwRequest request) {
        User user = userService.getUserOne(request.getUserId());
        if(user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        userService.resetPw(request);
        return ResponseEntity.ok().build();
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
