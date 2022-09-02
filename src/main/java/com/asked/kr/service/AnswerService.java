package com.asked.kr.service;

import com.asked.kr.domain.Answer;
import com.asked.kr.domain.AnswerCheck;
import com.asked.kr.domain.Ask;
import com.asked.kr.dto.req.AnswerReqDto;
import com.asked.kr.dto.req.AnswerUpdateDto;
import com.asked.kr.repository.AnswerRepository;
import com.asked.kr.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AskRepository askRepository;
    private final AnswerRepository answerRepository;
    private final MemberService memberService;

    @Transactional
    public Long writeAnswer(Long askIdx, AnswerReqDto answerReqDto){
        Ask ask = askRepository.findById(askIdx)
                .orElseThrow(() -> new RuntimeException());
        ask.updateCheck(AnswerCheck.COMPLETE);
        if(memberService.getCurrentMember() != ask.getReceiver()){
            throw new RuntimeException();
        }
        Answer answer = answerReqDto.toEntity(ask);
        return answerRepository.save(answer).getId();
    }

    @Transactional
    public void updateAnswer(Long answerIdx, AnswerUpdateDto answerDto){
        Answer answer = answerRepository.findById(answerIdx)
                .orElseThrow(() -> new RuntimeException());
        if(memberService.getCurrentMember() == answer.getAsk().getReceiver()){
            throw new RuntimeException();
        }
        answer.update(answerDto);
    }
}
