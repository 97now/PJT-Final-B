package com.ssafy.pjtFinal.interceptor;

import com.ssafy.pjtFinal.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
//        // 조회는 누구나 가능
//        if("GET".equalsIgnoreCase(request.getMethod())) {
//            return true;
//        }
//
//        // 그 외 요청은 로그인된 유저만 가능
//        HttpSession session = request.getSession(false);    // 세션 없으면 null 반환
//        if(session == null || session.getAttribute("loginUser") == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getMessage());   // 상태코드와 메시지 담아서 반환
//            return false;
//        }

        return true;
    }
}
