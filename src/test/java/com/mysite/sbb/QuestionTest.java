package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.repository.QuestionRepository;

import lombok.extern.java.Log;

@Log
@SpringBootTest
public class QuestionTest {

	@Autowired
	private QuestionRepository questionRepository;
	
	// [ INSERT ]
	//@Test
	@DisplayName("1. 질문 추가")
	@Transactional
	@Commit
	public void testQuestionInsert() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}
	
	// [ SELECT ]
	//@Test
	@DisplayName("1. 전체 데이터 조회")
	public void testQuestionSelect01() {
		List<Question> questionList = this.questionRepository.findAll();
		assertEquals(2, questionList.size());
		
		Question q = questionList.get(0);
		assertEquals("sbb는 무엇인가요?", q.getSubject());
	}
	
	//@Test
	@DisplayName("2. 아이디 데이터 조회")
	public void testQuestionSelect02() {
		Optional<Question> question = this.questionRepository.findById(1L);
		assertEquals("sbs에 대해서 알고 싶습니다.", question.get().getContent());
	}	
	
	//@Test
	@DisplayName("3. 제목으로 데이터 조회")
	public void testQuestionSelect03() {
		Optional<Question> question = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals("sbs에 대해서 알고 싶습니다.", question.get().getContent());
	}	
	
	// [ DELETE ]
	//@Test
	@DisplayName("1. 제목으로 데이터 조회")
	@Transactional
	@Commit
	public void testQuestionDelete01() {
		//this.questionRepository.deleteById(1L);
		
		//Optional<Question> question = this.questionRepository.findById(1L);
		//assertTrue(question.isPresent());
		
		//Optional<Question> question = this.questionRepository.findById(2L);
		//assertTrue(question.isPresent());
	}
	
	// [ UPDATE ]
	//@Test
	@DisplayName("1. 아이디에 대한 제목을 수정")
	@Transactional
	@Commit
	public void testQuestionUpdate01() {
		// 1단계: 객체를 획득
		Optional<Question> question = this.questionRepository.findById(2L);
		
		// 2단계: 획득한 객체를 수정
		Question q = question.get();
		q.setSubject("Spring Boot Model");
		
		// 3단계: 수정한 객체를 저장
		this.questionRepository.save(q);
		
		// 확인
		Optional<Question> q2 = this.questionRepository.findById(2L);
		// q2의 제목과 "Spring Boot Model"이 같은지의 여부를 판단
		assertEquals("Spring Boot Model", q2.get().getSubject());

		// q2가 존재하는지의 여부
//		assertTrue(q2.isPresent());
	}
	
	// ###################################################################
	
	// 1. 1번 질문에 달린 답변을 조회
	//@Test
	@DisplayName("1. 1번 질문에 달린 답변을 조회")
	@Transactional	
	public void testQuestionSelect04() {
		Optional<Question> question = this.questionRepository.findById(1L);
		
		if (question.isPresent()) {
			Question q = question.get();
			log.info("1번 질문:" + q.getSubject());
			
			List<Answer> answerList = q.getAnswerList();
			answerList.forEach(a -> log.info(a.getContent()));			
		} else {
			log.info("1번 질문은 존재하지 않습니다.");
		}			
	}
	
	// 2. 2번 질문에 달린 답변을 조회
	@Test
	@DisplayName("2. 2번 질문에 달린 답변을 조회")
	@Transactional	
	public void testQuestionSelect05() {
		Optional<Question> question = this.questionRepository.findById(2L);
		
		if (question.isPresent()) {
			Question q = question.get();
			log.info("2번 질문:" + q.getSubject());
			
			List<Answer> answerList = q.getAnswerList();
			answerList.forEach(a -> log.info(a.getContent()));
			log.info("2번 질문의 답변 개수: " + answerList.size());
		} else {
			log.info("2번 질문은 존재하지 않습니다.");
		}			
	}	
}
