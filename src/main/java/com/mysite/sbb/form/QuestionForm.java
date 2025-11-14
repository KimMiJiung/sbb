package com.mysite.sbb.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 질문 폼의 데이터를 전달하고, 유효성을 검사하기 위한 클래스
@Getter
@Setter
public class QuestionForm {
	@NotBlank(message = "질문 제목이 공백이어서는 안됩니다")
	@Size(max = 200)
	private String subject;
	
	@NotBlank(message = "질문 내용이 공백이어서는 안됩니다")
	private String content;
}
