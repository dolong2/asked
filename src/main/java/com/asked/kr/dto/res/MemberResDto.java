package com.asked.kr.dto.res;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class MemberResDto {
    private Long id;
    private String name;
    private String email;
    private List<AskResDto> ask;
}
