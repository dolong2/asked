package com.asked.kr.controller;

import com.asked.kr.dto.req.AnswerReqDto;
import com.asked.kr.dto.req.AnswerUpdateDto;
import com.asked.kr.response.ResponseService;
import com.asked.kr.response.result.CommonResult;
import com.asked.kr.service.impl.AnswerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AnswerController {
    private final AnswerServiceImpl answerService;
    private final ResponseService responseService;
    @PostMapping("/answer/{askIdx}")
    public CommonResult writeAnswer(@PathVariable Long askIdx, @RequestBody AnswerReqDto answerReqDto){
        answerService.writeAnswer(askIdx, answerReqDto);
        return responseService.getSuccessResult();
    }
    @PutMapping("/answer/{askIdx}")
    public CommonResult fixComment(@PathVariable Long askIdx, @RequestBody AnswerUpdateDto answerUpdateDto){
        answerService.updateAnswer(askIdx, answerUpdateDto);
        return responseService.getSuccessResult();
    }
}
