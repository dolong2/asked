package com.asked.kr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ask {
    @Id
    @GeneratedValue
    @Column(name = "Ask_id")
    private Long id;

    private String content;

    @OneToOne(mappedBy = "ask")
    private Answer answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private AnswerCheck check;

    public void updateCheck(AnswerCheck check){
        this.check=check;
    }
}
