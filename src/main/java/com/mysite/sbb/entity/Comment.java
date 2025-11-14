package com.mysite.sbb.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//댓글 엔터티
@Entity
@Getter
@Setter
@ToString
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
	@JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SiteUser author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
	@JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)    
    private Question question;

    @ManyToOne
	@JoinColumn(name = "answer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)   
    private Answer answer;
    
    public Long getQuestionId() {
        Long result = null;
        if (this.question != null) {
            result = this.question.getId();
        } else if (this.answer != null) {
            result = this.answer.getQuestion().getId();
        }
        return result;
    }  
}
