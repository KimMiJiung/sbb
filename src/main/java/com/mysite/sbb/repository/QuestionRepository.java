package com.mysite.sbb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	// 제목으로 조회
	Optional<Question> findBySubject(String subject);
	
	// 전체 목록 -> 페이징 처리
	Page<Question> findAll(Pageable pageable);
	
	// 전체 목록 1 -> 페이징 처리를 함, 검색 기능을 추가
	// - JPA에서 지원하는 Specification을 활용하는 방법
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	
	// 전체 목록 2 -> 페이징 처리를 함, 검색 기능을 추가	
	// - @Query를 사용하여 직접 SQL을 작성하는 방법
	// nativeQuery=true: 테이블 기준, nativeQuery=false: 엔터티 기준
	public final String QUESTION_SEARCH = 
			"select q from Question q "
			+ " left join SiteUser u1 on q.author = u1 "
			+ " left join Answer a on a.question = q "
			+ " left join SiteUser u2 on a.author = u2 "
			+ " where q.subject like %:kw% "
			+ " or q.content like %:kw% "
			+ " or u1.username like %:kw% "
			+ " or a.content like %:kw% "
			+ " or u2.username like %:kw% ";
	@Query(value = QUESTION_SEARCH, nativeQuery = false)
	Page<Question> querySearch(@Param("kw") String kw, Pageable pageable); 
	
	public final String QUESTION_SEARCH_SUBJECT =
			"select q from Question q "
			+ " left join SiteUser u1 on q.author = u1 "
			+ " left join Answer a on a.question = q "
			+ " left join SiteUser u2 on a.author = u2 "
			+ " where q.subject like %:kw% ";
	@Query(value = QUESTION_SEARCH_SUBJECT, nativeQuery = false)
	Page<Question> querySearchQuesSubject(@Param("kw") String kw, Pageable pageable);
	
	public final String QUESTION_SEARCH_CONTENT =
			"select q from Question q "
			+ " left join SiteUser u1 on q.author = u1 "
			+ " left join Answer a on a.question = q "
			+ " left join SiteUser u2 on a.author = u2 "
			+ " where q.content like %:kw% ";
	@Query(value = QUESTION_SEARCH_CONTENT, nativeQuery = false)
	Page<Question> querySearchQuesContent(@Param("kw") String kw, Pageable pageable);
	
	public final String QUESTION_SEARCH_USERNAME =
			"select q from Question q "
			+ " left join SiteUser u1 on q.author = u1 "
			+ " left join Answer a on a.question = q "
			+ " left join SiteUser u2 on a.author = u2 "
			+ " where u1.username like %:kw% ";
	@Query(value = QUESTION_SEARCH_USERNAME, nativeQuery = false)
	Page<Question> querySearchQuesUserName(@Param("kw") String kw, Pageable pageable);		

	public final String ANSWER_SEARCH_CONTENT =
			"select q from Question q "
			+ " left join SiteUser u1 on q.author = u1 "
			+ " left join Answer a on a.question = q "
			+ " left join SiteUser u2 on a.author = u2 "
			+ " where a.content like %:kw% ";
	@Query(value = ANSWER_SEARCH_CONTENT, nativeQuery = false)
	Page<Question> querySearchAnswContent(@Param("kw") String kw, Pageable pageable);		

	public final String ANSWER_SEARCH_AUTHOR =
			"select q from Question q "
			+ " left join SiteUser u1 on q.author = u1 "
			+ " left join Answer a on a.question = q "
			+ " left join SiteUser u2 on a.author = u2 "
			+ " where u2.username like %:kw% ";
	@Query(value = ANSWER_SEARCH_AUTHOR, nativeQuery = false)
	Page<Question> querySearchAnswAuthor(@Param("kw") String kw, Pageable pageable);
}
