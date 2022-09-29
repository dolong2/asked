package com.asked.kr.service;

import com.asked.kr.dto.req.AskReqDto;
import com.asked.kr.dto.res.AskResDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AskService {

    public Long write(AskReqDto askReqDto, String memberEmail);

    public List<AskResDto> getAll(String memberEmail);

    public void refuseAsk(Long askIdx);
}
