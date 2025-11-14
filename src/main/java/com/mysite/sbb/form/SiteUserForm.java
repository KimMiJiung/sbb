package com.mysite.sbb.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserForm {
	
	@NotBlank(message = "사용자 ID는 필수 입력 항목입니다.")
	@Size(min = 3, max = 20, message = "사용자 ID는 크기가 3 ~ 20사이여야 합니다.")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	private String password1;
	
	@NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
	private String password2;
	
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Email
	private String email;
}
