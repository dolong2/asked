package com.asked.kr.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignInDto {
    @NotBlank(message = "email should be valid")
    private String email;
    @NotBlank(message = "password should be valid")
    private String password;
}
