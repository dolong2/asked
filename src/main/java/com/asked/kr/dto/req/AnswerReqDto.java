package com.asked.kr.dto.req;

import com.asked.kr.domain.Answer;
import com.asked.kr.domain.Ask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AnswerReqDto {
    @NotBlank(message = "comment should be valid")
    private String content;

    public Answer toEntity(Ask ask){
        return Answer.builder()
                .ask(ask)
                .content(content)
                .build();
    }
}
