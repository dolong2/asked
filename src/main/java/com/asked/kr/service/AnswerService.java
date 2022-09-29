package com.asked.kr.service;

import com.asked.kr.dto.req.AnswerReqDto;
import com.asked.kr.dto.req.AnswerUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface AnswerService {

    public Long writeAnswer(Long askIdx, AnswerReqDto answerReqDto);

    public void updateAnswer(Long answerIdx, AnswerUpdateDto answerDto);
}
