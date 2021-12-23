package com.asked.kr.controller;

import com.asked.kr.domain.Ask;
import com.asked.kr.dto.AskDto;
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
    public CommonResult write(@RequestBody AskDto askDto, @PathVariable String memberEmail){
        askService.write(askDto,memberEmail);
        return responseService.getSuccessResult();
    }
    @GetMapping("/ask/{memberEmail}")
    public ListResult<Ask> getByMember(@PathVariable String memberEmail){
        return responseService.getListResult(askService.getAll(memberEmail));
    }
}
