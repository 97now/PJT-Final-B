package com.ssafy.pjtFinal.config;

import com.ssafy.pjtFinal.interceptor.SessionInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan(basePackages = "com.ssafy.pjtFinal.model.dao")
public class WebConfig implements WebMvcConfigurer {
    // interceptor 의존성 주입
    private SessionInterceptor sessionInterceptor;
    @Autowired
    public WebConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user",         // POST 회원가입
                        "/api/user/",        // 혹시 슬래시 붙은 경우도 포함
                        "/api/user/**",      // 단건조회, 리스트 조회 등
                        "/api/user/login"    // 로그인
                );
    }

    // spring security 쓸 거면 SecurityConfig에 넣는 게 맞대
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
}
