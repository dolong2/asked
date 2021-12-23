package com.asked.kr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class CommentDto {
    @NotBlank(message = "comment should be valid")
    private String comment;
}
