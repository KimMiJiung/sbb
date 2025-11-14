package com.mysite.sbb.role;

import lombok.Getter;

// enum: 열거형 데이터 타입, 
@Getter
public enum UserRole {
	// 열거형 상수(값)
	ADMIN("ROLL_ADMIN"), USER("ROLL_USER");
	
	private String value;
	
	UserRole(String value) {
		this.value = value;
	}
}
