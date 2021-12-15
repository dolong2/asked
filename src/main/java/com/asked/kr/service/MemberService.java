package com.asked.kr.service;

import com.asked.kr.domain.Member;
import com.asked.kr.dto.MemberDto;
import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.EmailDuplicateException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long join(MemberDto memberDto){
        Member member = memberDto.toEntity();
        if(!memberRepository.findByEmail(member.getEmail()).isEmpty()){
           throw new EmailDuplicateException("중복되는 회원이 있습니다", ErrorCode.EMAIL_DUPLICATION);
        }
        memberRepository.save(member);
        return member.getId();
    }
    public void login(){}
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
