package com.asked.kr.domain;

import com.asked.kr.dto.req.AnswerUpdateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @OneToOne
    @JsonIgnore
    private Ask ask;

    public void update(AnswerUpdateDto answerUpdateDto){
        this.content=answerUpdateDto.getContent();
    }
}
