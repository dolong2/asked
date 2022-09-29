package com.asked.kr.controller;

import com.asked.kr.dto.req.MemberReqDto;
import com.asked.kr.dto.req.SignInDto;
import com.asked.kr.dto.res.MemberResDto;
import com.asked.kr.response.ResponseService;
import com.asked.kr.response.result.CommonResult;
import com.asked.kr.response.result.ListResult;
import com.asked.kr.response.result.SingleResult;
import com.asked.kr.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MemberController {
    private final MemberServiceImpl memberService;
    private final ResponseService responseService;
    @PostMapping("/join")
    public CommonResult join(@RequestBody MemberReqDto memberReqDto){
        memberService.join(memberReqDto);
        return responseService.getSuccessResult();
    }
    @PostMapping("/logout")
    public CommonResult logout(){
        memberService.logout();
        return responseService.getSuccessResult();
    }
    @PostMapping("/login")
    public SingleResult<Map<String,String>> login(@RequestBody SignInDto signInDto){
        return responseService.getSingleResult(memberService.login(signInDto));
    }
    @GetMapping("/member")
    public ListResult<MemberResDto> findAll(){
        return responseService.getListResult(memberService.findAll());
    }
    @GetMapping("/member/{memberEmail}")
    public SingleResult<MemberResDto> findOne(@PathVariable String memberEmail){
        return responseService.getSingleResult(memberService.findOne(memberEmail));
    }
    @PutMapping("/member")
    public CommonResult update(@RequestBody MemberReqDto memberReqDto){
        memberService.Update(memberReqDto);
        return responseService.getSuccessResult();
    }

}
