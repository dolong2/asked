package com.asked.kr.service;

import com.asked.kr.dto.req.MemberReqDto;
import com.asked.kr.dto.req.SignInDto;
import com.asked.kr.dto.res.MemberResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MemberService {
    Long join(MemberReqDto memberReqDto);
    Map<String, String> login(SignInDto signInDto);
    void logout();
    void Update(MemberReqDto memberReqDto);
    MemberResDto findOne(String memberEmail);
    List<MemberResDto> findAll();
}
