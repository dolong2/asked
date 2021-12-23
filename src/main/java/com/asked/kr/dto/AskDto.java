package com.asked.kr.dto;

import com.asked.kr.domain.Ask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AskDto {
    @NotBlank(message = "content should be valid")
    private String content;
    public Ask toEntity(){
        return Ask.builder()
                .content(this.content)
                .build();
    }
}
