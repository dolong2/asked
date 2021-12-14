package com.asked.kr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class Ask {
    @Id
    @GeneratedValue
    @Column(name = "Ask_id")
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    public Ask() {}
}
