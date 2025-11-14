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

// 답변 엔터티
@Entity
@Getter
@Setter
@ToString
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "text")
	private String content;
	
	// 등록 날짜와 시간
	@CreatedDate
	private LocalDateTime createDate;
	
	// 수정 날짜와 시간
	private LocalDateTime modifyDate;
	
	/*
	 * Answer과 Question의 관계
	 * Answer(답변) : Question(질문) -> N(다) : 1의 관계
	 * - 답변 여러 개는 질문 하나를 참조함.
	 * - Answer 엔터티: 자식 엔터티
	 * - Question 엔터티: 부모 엔터티
	 * - 답변은 질문을 참조해야 함. 
	 */	
	@ManyToOne
	private Question question;
	
	/* 작성자 추가
	 * < Answer(답변)과 SiteUser(작성자, author)의 관계 >
	 * - Answer(답변) : SiteUser(작성자) -> N(다) : 1의 관계
	 * - 작성자는 답변을 여러개  작성할 수 있음
	 */
	@ManyToOne
	@JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)		
	private SiteUser author;
	
	// 추천인
	/*
	 * Answer(답변) : SiteUser(사용자) -> -> M(다) : N(다)의 관계
	 * - 1개의 답변에는 여러 사용자가 추천할 수 있고, 1명의 사용자는 여러 답변을 추천할 수 있음.
	 * - answer_voter 테이블이 생성 -> answer_id와 voter_id를 갖고 있음.
	 * - 사용자는 각 답변에 1번의 추천만 가능
	 */
	@ManyToMany
	@JoinTable(
	        name = "answer_voter", // 중간 테이블명
	        joinColumns = @JoinColumn(name = "id"), // SiteUser의 ID
	        inverseJoinColumns = @JoinColumn(name = "voter_id") // SiteUser의 추천인 ID
	)	
	private Set<SiteUser> voter;
	
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

}
