package com.asked.kr.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerUpdateDto {
    @NotBlank(message = "comment should be valid")
    private String content;
}
