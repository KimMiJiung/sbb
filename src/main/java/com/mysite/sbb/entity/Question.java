package com.mysite.sbb.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 질문 엔터티
@Entity
@Getter
@Setter
@ToString
public class Question{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "text")
	private String content;
	
	// 등록 날짜와 시간
	@CreatedDate
	private LocalDateTime createDate;
	
	// 수정 날짜와 시간
	private LocalDateTime modifyDate;
	
	/*
	 * < Question(질문)과 Answer(답변)의 관계 >
	 * - Question(질문) : Answer(답변) -> 1 : N(다)의 관계
	 * - 1개의 질문에는 여러 개의 답변이 달릴 수 있음.
	 * - mappedBy: 연결된 엔터티의 Answer에 있는 속성명
	 * - cascade: CascadeType.REMOVE은 질문을 삭제하면, 질문과 관련된 답변도 삭제함.
	 */	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	/* 작성자 (글쓴이)
	 * < Question(질문)과 SiteUser(작성자, author)의 관계 >
	 * - Question(질문) : SiteUser(작성자) -> N(다) : 1의 관계
	 * - 작성자는 여러 개의 질문을 작성할 수 있음.
	 */
	@ManyToOne
	@JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)	
	private SiteUser author;
	
	// 추천인
	/*
	 * Question(질문) : SiteUser(사용자) -> -> M(다) : N(다)의 관계
	 * - 1개의 질문에는 여러 사용자가 추천할 수 있고, 1명의 사용자는 여러 질문을 추천할 수 있음.
	 * - question_voter 테이블을 생성 (question_id와 voter_id를 갖고 있음.)
	 * - Set: 중복을 허용하지 않음, 같은 사용자가 같은 질문에 2번 추천할 수는 없음.
	 * - 사용자는 각 답변에 1번의 추천만 가능
	 */
	@ManyToMany
	@JoinTable(
	        name = "question_voter", // 중간 테이블명
	        joinColumns = @JoinColumn(name = "id"), // SiteUser의 ID
	        inverseJoinColumns = @JoinColumn(name = "voter_id") // SiteUser의 추천인 ID
	)	
	private Set<SiteUser> voter;	
	
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
	
}
