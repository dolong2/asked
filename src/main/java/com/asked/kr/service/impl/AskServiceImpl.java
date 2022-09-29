package com.asked.kr.service.impl;

import com.asked.kr.domain.AnswerCheck;
import com.asked.kr.domain.Ask;
import com.asked.kr.domain.Member;
import com.asked.kr.dto.req.AskReqDto;
import com.asked.kr.dto.res.AskResDto;
import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.AskNotFindException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.exception.exceptions.NotSameReceiverException;
import com.asked.kr.repository.AskRepository;
import com.asked.kr.repository.MemberRepository;
import com.asked.kr.service.AskService;
import com.asked.kr.util.ResponseDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AskServiceImpl implements AskService {
    private final AskRepository askRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long write(AskReqDto askReqDto, String memberEmail){
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new NoMemberException("해당 유저가 존재하지 않습니다.", ErrorCode.NO_MEMBER));
        Ask ask = askReqDto.toEntity(member);
        askRepository.save(ask);
        return ask.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AskResDto> getAll(String memberEmail){
        List<AskResDto> result = ResponseDtoUtil.mapAll(askRepository.findAskByReceiverEmail(memberEmail), AskResDto.class);
        return result;
    }

    @Override
    @Transactional
    public void refuseAsk(Long askIdx){
        Ask ask = askRepository.findById(askIdx)
                .orElseThrow(() -> new AskNotFindException("질문을 찾을 수 없습니다.", ErrorCode.NOT_FIND_ASK));
        Member member = memberRepository.findByEmail(MemberServiceImpl.getUserEmail())
                .orElseThrow(() -> new NoMemberException("멤버가 존재하지 않습니다.", ErrorCode.NO_MEMBER));
        if(ask.getReceiver()!=member){
            throw new NotSameReceiverException("수신자가 일치하지 않습니다.", ErrorCode.NOT_SAME_MEMBER);
        }
        ask.updateCheck(AnswerCheck.REFUSED);
    }
}
