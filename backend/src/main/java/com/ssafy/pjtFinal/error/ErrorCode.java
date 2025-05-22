package com.ssafy.pjtFinal.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 공통 에러
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력 형식이 올바르지 않습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 알 수 없는 오류가 발생했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "콘텐츠가 비어있습니다"),

    // 유저 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다"),
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST, "이미 가입된 아이디입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다"),

    // 팔로우 관련
    NO_FOLLOWING(HttpStatus.OK, "팔로잉한 유저가 없습니다"),
    NO_FOLLOWER(HttpStatus.OK, "나를 팔로우한 유저가 없습니다"),
    ALREADY_FOLLOWED(HttpStatus.CONFLICT, "이미 팔로우한 유저입니다"),

    // 좋아요 관련
    NO_LIKE(HttpStatus.OK, "좋아요 한 영상이 없습니다"),
    ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요 한 영상입니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
