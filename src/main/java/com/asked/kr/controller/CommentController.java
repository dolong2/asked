package com.asked.kr.controller;

import com.asked.kr.dto.CommentDto;
import com.asked.kr.response.ResponseService;
import com.asked.kr.response.result.CommonResult;
import com.asked.kr.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CommentController {
    private final AskService askService;
    private final ResponseService responseService;
    @PostMapping("/comment/{askIdx}")
    public CommonResult writeComment(@PathVariable Long askIdx, @RequestBody CommentDto commentDto){
        askService.addComment(askIdx,commentDto);
        return responseService.getSuccessResult();
    }
    @PutMapping("/comment/{askIdx}")
    public CommonResult fixComment(@PathVariable Long askIdx, @RequestBody CommentDto commentDto){
        askService.fixComment(askIdx,commentDto);
        return responseService.getSuccessResult();
    }
}
