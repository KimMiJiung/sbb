package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.repository.QuestionRepository;
import com.mysite.sbb.repository.UserRepository;

import lombok.extern.java.Log;

@Log
@SpringBootTest
public class QuestionTest02 {
	
	@Autowired
	private  QuestionRepository questionRepository;
	@Autowired
	private UserRepository userRepository;
	
	// 페이징 처리에서 활용 -> 355건의 질문 데이터를 추가
	@Test
	@DisplayName("1. 페이징 처리 사용할 대량의 질문 데이터 추가")
	@Transactional
	@Commit
	public void testInsert01() {
		for (int i = 1; i <= 70; i++) {
			Question q = new Question();
			Optional<SiteUser> user = this.userRepository.findById(1L);
			q.setSubject(String.format("스프링 부트 제목 - - %03d", i));
			q.setContent(String.format("스프링 부트 내용 - - %03d", i));					
			q.setCreateDate(LocalDateTime.now());
			q.setAuthor(user.get());
			this.questionRepository.save(q);
		}
		for (int i = 71; i <= 140; i++) {
			Question q = new Question();
			Optional<SiteUser> user = this.userRepository.findById(2L);
			q.setSubject(String.format("스프링 부트 제목 - - %03d", i));
			q.setContent(String.format("스프링 부트 내용 - - %03d", i));					
			q.setCreateDate(LocalDateTime.now());
			q.setAuthor(user.get());
			this.questionRepository.save(q);
		}
		for (int i = 141; i <= 210; i++) {
			Question q = new Question();
			Optional<SiteUser> user = this.userRepository.findById(3L);
			q.setSubject(String.format("스프링 부트 제목 - - %03d", i));
			q.setContent(String.format("스프링 부트 내용 - - %03d", i));					
			q.setCreateDate(LocalDateTime.now());
			q.setAuthor(user.get());
			this.questionRepository.save(q);
		}
		for (int i = 211; i <= 280; i++) {
			Question q = new Question();
			Optional<SiteUser> user = this.userRepository.findById(4L);
			q.setSubject(String.format("스프링 부트 제목 - - %03d", i));
			q.setContent(String.format("스프링 부트 내용 - - %03d", i));					
			q.setCreateDate(LocalDateTime.now());
			q.setAuthor(user.get());
			this.questionRepository.save(q);
		}
		for (int i = 281; i <= 355; i++) {
			Question q = new Question();
			Optional<SiteUser> user = this.userRepository.findById(5L);
			q.setSubject(String.format("스프링 부트 제목 - - %03d", i));
			q.setContent(String.format("스프링 부트 내용 - - %03d", i));					
			q.setCreateDate(LocalDateTime.now());
			q.setAuthor(user.get());
			this.questionRepository.save(q);
		}		
	}	
}
