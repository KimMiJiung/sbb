package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.repository.AnswerRepository;
import com.mysite.sbb.repository.QuestionRepository;

import lombok.extern.java.Log;

@Log
@SpringBootTest
public class AnswerTest {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired	
	private QuestionRepository questionRepository;
	
	// [ INSERT ]
	//@Test
	@DisplayName("1. 답변 추가 - 1번글에 대한 답변")
	@Transactional
	@Commit
	public void testAnswerInsert01() {
		Optional<Question> question = questionRepository.findById(1L);
		
		// 1번 글에 대한 답변 2개
		Answer answer = new Answer();
		answer.setContent("자바 기반의 웹플임워크입니다.");
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question.get());
		this.answerRepository.save(answer);
		
		Answer answer2 = new Answer();
		answer2.setContent("스프링부트는 JPA로 DB의 테이블을 관리합니다.");
		answer2.setCreateDate(LocalDateTime.now());
		answer2.setQuestion(question.get());
		this.answerRepository.save(answer2);
	}
	
	@Test
	@DisplayName("2. 답변 추가 - 1번글에 대한 답변")
	@Transactional
	@Commit	
	public void testAnswerInsert02() {
		Optional<Question> question = questionRepository.findById(2L);
		
		// 1번 글에 대한 답변 2개
		Answer answer = new Answer();
		answer.setContent("JpaRepository 인터페이스를 상속한 레파지토리를 생성합니다.");
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question.get());
		this.answerRepository.save(answer);
		
		Answer answer2 = new Answer();
		answer2.setContent("JpaRepository 인터페이스에 있는 쿼리 메서드를 사용합니다.");
		answer2.setCreateDate(LocalDateTime.now());
		answer2.setQuestion(question.get());
		this.answerRepository.save(answer2);
	}	
	
}
