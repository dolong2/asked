package com.asked.kr.service;

import com.asked.kr.config.security.jwt.TokenProvider;
import com.asked.kr.domain.Member;
import com.asked.kr.dto.MemberDto;
import com.asked.kr.dto.SignInDto;
import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.EmailDuplicateException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    public void logout(){}
    public void Update(Long memberIdx,MemberDto memberDto){
        Member byId = memberRepository.getById(memberIdx);
        if(byId==null){
            throw new NoMemberException("해당 유저를 찾을 수 없습니다",ErrorCode.NO_MEMBER);
        }
        Member member = memberDto.toEntity();
        byId=member;
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
}
