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
    private String comment;//굳이 질문과 답변이 1:1테이블로 안 만들고 간단하게 이래도 될듯?
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;


    public Ask() {}

    public void setMember(Member member) {
        this.member = member;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
}
