package com.asked.kr.dto.req;

import com.asked.kr.domain.AnswerCheck;
import com.asked.kr.domain.Ask;
import com.asked.kr.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AskReqDto {
    @NotBlank(message = "content should be valid")
    private String content;
    public Ask toEntity(Member member){
        return Ask.builder()
                .receiver(member)
                .content(this.content)
                .check(AnswerCheck.WAITING)
                .build();
    }
}
