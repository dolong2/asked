package com.asked.kr.controller;

import com.asked.kr.domain.Member;
import com.asked.kr.dto.MemberDto;
import com.asked.kr.response.ResponseService;
import com.asked.kr.response.result.CommonResult;
import com.asked.kr.response.result.ListResult;
import com.asked.kr.response.result.SingleResult;
import com.asked.kr.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MemberController {
    private final MemberService memberService;
    private final ResponseService responseService;
    @PostMapping("/join")
    public CommonResult join(@RequestBody MemberDto memberDto){
        memberService.join(memberDto);
        return responseService.getSuccessResult();
    }
    @GetMapping("/member")
    public ListResult<Member> findAll(){
        return responseService.getListResult(memberService.findAll());
    }
    @GetMapping("/member/{memberIdx}")
    public SingleResult<Member> findOne(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.findOne(memberIdx));
    }
}
