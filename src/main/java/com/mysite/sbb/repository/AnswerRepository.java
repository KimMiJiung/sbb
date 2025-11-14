package com.mysite.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{
	 
}
