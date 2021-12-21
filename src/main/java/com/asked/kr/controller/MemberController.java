package com.asked.kr.controller;

import com.asked.kr.domain.Member;
import com.asked.kr.dto.MemberDto;
import com.asked.kr.dto.SignInDto;
import com.asked.kr.response.ResponseService;
import com.asked.kr.response.result.CommonResult;
import com.asked.kr.response.result.ListResult;
import com.asked.kr.response.result.SingleResult;
import com.asked.kr.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping("/login")
    public SingleResult<Map<String,String>> login(@RequestBody SignInDto signInDto){
        return responseService.getSingleResult(memberService.login(signInDto));
    }
    @GetMapping("/member")
    public ListResult<Member> findAll(){
        return responseService.getListResult(memberService.findAll());
    }
    @GetMapping("/member/{memberEmail}")
    public SingleResult<Member> findOne(@PathVariable String memberEmail){
        return responseService.getSingleResult(memberService.findOne(memberEmail));
    }
    @PutMapping("/member")
    public CommonResult update(@RequestBody MemberDto memberDto){
        memberService.Update(memberDto);
        return responseService.getSuccessResult();
    }

}
