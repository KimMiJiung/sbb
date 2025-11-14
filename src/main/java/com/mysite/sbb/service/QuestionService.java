package com.mysite.sbb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.repository.QuestionRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	// 빈을 등록
	private final QuestionRepository questionRepository;

	// 글 전체 -> 페이징 처리를 하지 않음, 검색 기능이 없음
	/*
	public List<Question> getList() {
		return this.questionRepository.findAll();
	}
	*/
	
	// 글 전체 조회 - 페이징 처리를 함, 검색 기능이 없음
	/*
	public Page<Question> getList(int page) {
		// 등록일에 대한 내림차순 정렬
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		// page: 현재 페이지 번호, 10:한 페이지에 보여줄 건수
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));  
		return this.questionRepository.findAll(pageable);
	}
	*/
	
	// 글 목록 조회 -> 페이징 처리를 함, 검색 기능을 추가
	public Page<Question> getList(int page, String kw, String searchType) {
		// 등록일에 대한 내림차순 정렬
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		// page: 현재 페이지 번호, 10:한 페이지에 보여줄 건수
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		
		// 검색 1번 방법 - JPA에서 제공하는 Specification 인터페이스를 활용
		Specification<Question> spec = search(kw, searchType);
		return this.questionRepository.findAll(spec, pageable);
		
		// 검색 2번 방법 - @Query 애너테이션을 사용하여 쿼리문을 직접 작성
//		Page<Question> querySearch = null;
//		if (searchType.isEmpty() || searchType.isBlank()) {
//			querySearch = this.questionRepository.querySearch(kw, pageable);
//		} else if ("ques_title".equals(searchType)) {
//			querySearch = this.questionRepository.querySearchQuesSubject(kw, pageable);
//		} else if ("ques_content".equals(searchType)) {
//			querySearch = this.questionRepository.querySearchQuesContent(kw, pageable);
//		} else if ("ques_writer".equals(searchType)) {
//			querySearch = this.questionRepository.querySearchQuesUserName(kw, pageable);
//		} else if ("answ_writer".equals(searchType)) {
//			querySearch = this.questionRepository.querySearchAnswAuthor(kw, pageable);
//		} else if ("answ_content".equals(searchType)) {
//			querySearch = this.questionRepository.querySearchAnswContent(kw, pageable);
//		}
//				
//		return querySearch;
	}
	
	// 검색 - 질문 글제목, 글내용, 질문 작성자, 답변 글내용, 답변 작성자
	public Specification<Question> search(String kw, String searchType) {
		return new Specification<>() {
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);  // 중복 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);     // 질문과 사용자를 조인 
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);    // 질문과 답변을 조인
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);       // 답변과 사용자를 조인

				switch(searchType) {
					case "ques_title" :
						return cb.or(cb.like(q.get("subject"), "%" + kw + "%"));
					case "ques_content" :
						return cb.or(cb.like(q.get("content"), "%" + kw + "%"));
					case "ques_writer" :
						return cb.or(cb.like(u1.get("username"), "%" + kw + "%"));
					case "answ_writer" :
						return cb.or(cb.like(a.get("content"), "%" + kw + "%"));
					case "answ_content" :
						return cb.or(cb.like(u2.get("username"), "%" + kw + "%"));
					default :
						return cb.or( 
							cb.like(q.get("subject"), "%" + kw + "%"),           // 질문 글제목
							cb.like(q.get("content"), "%" + kw + "%"),           // 질문 글내용
							cb.like(u1.get("username"), "%" + kw + "%"),         // 질문 작성자
							cb.like(a.get("content"), "%" + kw + "%"),           // 답변 글내용
							cb.like(u2.get("username"), "%" + kw + "%")          // 답변 작성자
					);						
				}
			}
		};
	}

	// 글 1건 조회 
	public Question getQuestion(Long id) {
		Optional<Question> question =  this.questionRepository.findById(id);
		
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("Question Not Found!!!");
		}
	}
		
	// 글 등록
	public void create(String subject, String content, SiteUser siteUser) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		question.setAuthor(siteUser);
		this.questionRepository.save(question);
	}
	
	// 글 수정
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	// 글 삭제
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	// 추천 추가
	// - 질문에 추천을 추가하고, 다시 저장(수정)
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
	// 답변리스트 페이징처리
	public Page<Answer> getAnswerPage(int page, List<Answer> answerList) {
		Pageable pageable = PageRequest.of(page, 5);
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), answerList.size() );
		Page<Answer> answerPage = new PageImpl<> (answerList.subList(start, end), pageable, answerList.size());
		return answerPage;
	}
}
