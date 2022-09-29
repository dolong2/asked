package com.asked.kr.service.impl;

import com.asked.kr.config.security.jwt.TokenProvider;
import com.asked.kr.domain.Member;
import com.asked.kr.dto.req.MemberReqDto;
import com.asked.kr.dto.req.SignInDto;
import com.asked.kr.dto.res.AskResDto;
import com.asked.kr.dto.res.MemberResDto;
import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.EmailDuplicateException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.repository.AskRepository;
import com.asked.kr.repository.MemberRepository;
import com.asked.kr.service.MemberService;
import com.asked.kr.util.RedisUtil;
import com.asked.kr.util.ResponseDtoUtil;
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
public class MemberServiceImpl implements MemberService {
    private final AskRepository askRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    @Override
    public Long join(MemberReqDto memberReqDto){
        Member member = memberReqDto.toEntity(passwordEncoder.encode(memberReqDto.getPassword()));
        if(!memberRepository.findByEmail(member.getEmail()).isEmpty()){
           throw new EmailDuplicateException("중복되는 회원이 있습니다", ErrorCode.EMAIL_DUPLICATION);
        }
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public Map<String, String> login(SignInDto signInDto){
        Member member = memberRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new NoMemberException("이메일을 다시 입력해주세요",ErrorCode.NO_MEMBER));
        if(!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())){
            throw new NoMemberException("패스워드를 다시 입력해주세요",ErrorCode.NO_MEMBER);
        }
        final String accessToken = tokenProvider.generateAccessToken(member.getEmail());
        final String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());

        redisUtil.setData("refreshToken", refreshToken);

        Map<String,String> map=new HashMap<>();
        map.put("email", member.getEmail());
        map.put("accessToken",accessToken);
        map.put("refreshToken",refreshToken);
        return map;
    }

    @Override
    public void logout(){
        String userEmail = this.getUserEmail();
        redisUtil.deleteData(userEmail);
    }

    @Override
    public void Update(MemberReqDto memberReqDto){
        Member member = memberRepository.findByEmail(getUserEmail())
                .orElseThrow(()->new NoMemberException("해당 유저를 찾을 수 없습니다",ErrorCode.NO_MEMBER));
        Member entity = memberReqDto.toEntity(passwordEncoder.encode(memberReqDto.getPassword()));
        member = entity;
    }

    @Override
    public MemberResDto findOne(String memberEmail) throws IllegalStateException{
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(()->new NoMemberException("해당 유저를 찾을 수 없습니다",ErrorCode.NO_MEMBER));
        MemberResDto memberResDto = ResponseDtoUtil.mapping(member, MemberResDto.class);
        List<AskResDto> askList = ResponseDtoUtil.mapAll(askRepository.findAskByReceiverEmail(member.getEmail()), AskResDto.class);
        memberResDto.setAsk(askList);
        return memberResDto;
    }

    @Override
    public List<MemberResDto> findAll(){
        List<MemberResDto> result = ResponseDtoUtil.mapAll(memberRepository.findAll(), MemberResDto.class);
        result.forEach(memberResDto -> {
            List<AskResDto> askList = ResponseDtoUtil.mapAll(askRepository.findAskByReceiverEmail(memberResDto.getEmail()), AskResDto.class);
            memberResDto.setAsk(askList);
        });
        return result;
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

    public Member getCurrentMember(){
        String email = getUserEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoMemberException("해당 유저를 찾을 수 없습니다", ErrorCode.NO_MEMBER));
        return member;
    }
}
