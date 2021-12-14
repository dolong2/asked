package com.asked.kr.dto;

import com.asked.kr.domain.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MemberDto {
    @NotBlank(message = "email should be valid")
    private String email;
    @NotBlank(message = "password should be valid")
    private String password;
    @NotBlank(message = "name should be valid")
    private String name;
    public Member toEntity(){
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
