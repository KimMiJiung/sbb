package com.mysite.sbb.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

//답변 폼의 데이터를 전달하고, 유효성을 검사하기 위한 클래스
@Getter
@Setter
public class AnswerForm {
	
	@NotBlank(message = "답변 내용이 공백이어서는 안됩니다.")
	private String content;
}
