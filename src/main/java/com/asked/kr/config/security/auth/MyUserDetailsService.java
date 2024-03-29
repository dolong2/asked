package com.asked.kr.config.security.auth;

import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username).orElseThrow(()->new NoMemberException("해당 유저가 없습니다", ErrorCode.NO_MEMBER));
    }
}
