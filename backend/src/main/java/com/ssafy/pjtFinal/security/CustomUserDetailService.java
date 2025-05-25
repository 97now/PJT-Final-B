package com.ssafy.pjtFinal.security;

import com.ssafy.pjtFinal.model.dao.UserDao;
import com.ssafy.pjtFinal.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userDao.userSelectOne(userId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getUserPw(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
