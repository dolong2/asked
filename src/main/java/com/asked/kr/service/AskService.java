package com.asked.kr.service;

import com.asked.kr.domain.Ask;
import com.asked.kr.domain.Member;
import com.asked.kr.dto.AskDto;
import com.asked.kr.dto.CommentDto;
import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.exception.exceptions.NotSameMemberException;
import com.asked.kr.repository.AskRepository;
import com.asked.kr.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskService {
    private final AskRepository askRepository;
    private final MemberRepository memberRepository;
    public Long write(AskDto askDto,String memberEmail){
        List<Member> byEmail = memberRepository.findByEmail(memberEmail);
        if(byEmail.isEmpty()){
            throw new NoMemberException("해당 유저가 존재하지 않습니다", ErrorCode.NO_MEMBER);
        }
        Member member = byEmail.get(0);
        Ask ask = askDto.toEntity();
        ask.setMember(member);
        askRepository.save(ask);
        return ask.getId();
    }
    public List<Ask> getAll(String memberEmail){
        return askRepository.findByMemberEmail(memberEmail);
    }
    public void setComment(Long askIdx, CommentDto commentDto){
        Ask byId = askRepository.getById(askIdx);
        Member byIdMember = byId.getMember();
        List<Member> byEmail = memberRepository.findByEmail(MemberService.getUserEmail());
        if(byEmail.isEmpty()){
            throw new NoMemberException("로그인 먼저 해주세요",ErrorCode.NO_MEMBER);
        }
        Member member = byEmail.get(0);
        if(member!=byIdMember){
            throw new NotSameMemberException("유저가 일치하지 않습니다",ErrorCode.NOT_SAME_MEMBER);
        }
        byId.setComment(commentDto.getComment());
    }
}
