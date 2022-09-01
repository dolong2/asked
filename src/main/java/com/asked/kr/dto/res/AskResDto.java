package com.asked.kr.dto.res;

import com.asked.kr.domain.Answer;
import com.asked.kr.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AskResDto {
    private Long id;
    private String content;
    private Answer answer;
    private Member receiver;
}
