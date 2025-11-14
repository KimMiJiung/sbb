package com.mysite.sbb.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder  // 빌더 디자인 패턴을 사용하는 방법
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
	// 기본키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//@Column(columnDefinition = "varchar(30)", nullable = false)
	@Column(columnDefinition = "varchar(30)")
	private String customerId;

	//@Column(columnDefinition = "varchar(50)", unique = true)
	@Column(columnDefinition = "varchar(50)")
	private String name;
	
	// 남성, 여성
	//@Column(columnDefinition = "char(2) default '남성'", updatable = false)
	@Column(columnDefinition = "char(2) default '남성'")
	private String gender;
	
	private Integer age;
	
	// 010-xxxx-xxxx -> 13자리
	@Column(columnDefinition = "varchar(13)")
	private String phone;
	
	@Column(columnDefinition = "varchar(50)")
	private String email;
	
	@Column(columnDefinition = "text")
	private String address;
	
	@Column(columnDefinition = "datetime default now()")
	//@Temporal(TemporalType.TIMESTAMP)
	//@CreatedDate
	private LocalDateTime regDate;
	
	// id, regDate를 제외한 매개변수를 갖는 생성자
	public Customer(String customerId, String name, String gender, Integer age, String phone, String email, String address) {
		this.customerId = customerId;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.regDate = LocalDateTime.now(); 
	}
	
	
	
	
	
	
//	//@CreationTimestamp
//	private LocalDateTime insertDate;
//	
//	@UpdateTimestamp
//	private LocalDateTime updateDate;
	
	
	
	
	
}
