package com.asked.kr.service;

import com.asked.kr.domain.Member;
import com.asked.kr.dto.MemberDto;
import com.asked.kr.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long join(MemberDto memberDto){
        Member member = memberDto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }
    public void login(){}
    public void logout(){}
    public void Update(Long memberIdx,MemberDto memberDto){
        Member byId = memberRepository.getById(memberIdx);
        Member member = memberDto.toEntity();
        byId=member;
    }
    public Member findOne(Long memberIdx){
        return memberRepository.getById(memberIdx);
    }
}
