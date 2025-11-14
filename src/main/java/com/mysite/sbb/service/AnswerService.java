package com.mysite.sbb.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	// 답변 등록
	public Answer create(Question question, String content, SiteUser siteUser) {
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setAuthor(siteUser);
		this.answerRepository.save(answer);
		return answer;
	}
	
	// 답변 조회
	public Answer getAnswer(Long id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		
		if (answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("Answer Not Found!!!");
		}
	}
	
	// 답변 수정
	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	// 답변 삭제
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
	
	// 추천 추가
	// - 답변에 대해 추천인을 추가하여, 답변을 수정함.
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}
	

	
}
