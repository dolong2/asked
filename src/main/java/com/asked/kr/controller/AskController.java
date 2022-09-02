package com.asked.kr.controller;

import com.asked.kr.dto.req.AskReqDto;
import com.asked.kr.dto.res.AskResDto;
import com.asked.kr.response.ResponseService;
import com.asked.kr.response.result.CommonResult;
import com.asked.kr.response.result.ListResult;
import com.asked.kr.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AskController {
    private final AskService askService;
    private final ResponseService responseService;
    @PostMapping("/ask/{memberEmail}")
    public CommonResult write(@RequestBody AskReqDto askReqDto, @PathVariable String memberEmail){
        askService.write(askReqDto,memberEmail);
        return responseService.getSuccessResult();
    }
    @GetMapping("/ask/{memberEmail}")
    public ListResult<AskResDto> getByMember(@PathVariable String memberEmail){
        return responseService.getListResult(askService.getAll(memberEmail));
    }

    @PostMapping("/ask/refuse/{askIdx}")
    public CommonResult refuesAsk(@PathVariable Long askIdx){
        askService.refuseAsk(askIdx);
        return responseService.getSuccessResult();
    }
}
