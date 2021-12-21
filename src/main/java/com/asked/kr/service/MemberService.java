package com.asked.kr.service;

import com.asked.kr.config.security.jwt.TokenProvider;
import com.asked.kr.domain.Member;
import com.asked.kr.dto.MemberDto;
import com.asked.kr.dto.SignInDto;
import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.EmailDuplicateException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.repository.MemberRepository;
import com.asked.kr.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    public Long join(MemberDto memberDto){
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member = memberDto.toEntity();
        if(!memberRepository.findByEmail(member.getEmail()).isEmpty()){
           throw new EmailDuplicateException("중복되는 회원이 있습니다", ErrorCode.EMAIL_DUPLICATION);
        }
        memberRepository.save(member);
        return member.getId();
    }
    public Map<String, String> login(SignInDto signInDto){
        List<Member> byEmail = memberRepository.findByEmail(signInDto.getEmail());
        if(byEmail.isEmpty()){
            throw new NoMemberException("이메일을 다시 입력해주세요",ErrorCode.NO_MEMBER);
        }
        Member member = byEmail.get(0);
        Boolean check = passwordEncoder.matches(signInDto.getPassword(), member.getPassword());
        if(!check){
            throw new NoMemberException("패스워드를 다시 입력해주세요",ErrorCode.NO_MEMBER);
        }
        final String accessToken = tokenProvider.generateAccessToken(member.getEmail());
        final String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());

        Map<String,String> map=new HashMap<>();
        map.put("email", member.getEmail());
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);
        return map;
    }
    public void logout(){
        String userEmail = this.getUserEmail();
        redisUtil.deleteData(userEmail);
    }
    public void Update(MemberDto memberDto){
        List<Member> byEmail = memberRepository.findByEmail(getUserEmail());
        if(byEmail.isEmpty()){
            throw new NoMemberException("해당 유저를 찾을 수 없습니다",ErrorCode.NO_MEMBER);
        }
        Member byMail = byEmail.get(0);
        Member member = memberDto.toEntity();
        byMail=member;
    }
    public Member findOne(String memberEmail) throws IllegalStateException{
        List<Member> byEmail = memberRepository.findByEmail(memberEmail);
        if(byEmail.isEmpty()){
            throw new NoMemberException("해당 유저를 찾을 수 없습니다",ErrorCode.NO_MEMBER);
        }
        Member member = byEmail.get(0);
        return member;
    }
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    static public String getUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
