package com.asked.kr.dto.req;

import com.asked.kr.domain.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class MemberReqDto {
    @NotBlank(message = "email should be valid")
    private String email;
    @NotBlank(message = "password should be valid")
    private String password;
    @NotBlank(message = "name should be valid")
    private String name;
    public Member toEntity(String password){
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(password)
                .build();
    }
}
