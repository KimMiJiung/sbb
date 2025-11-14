package com.mysite.sbb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mysite.sbb.service.UserService;

// 프로젝트를 시작할 때 최초로 수행되는 클래스
@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbbApplication.class, args);
	}
	
	// SiteUser 등록
	//@Bean
	public CommandLineRunner run(UserService userService) throws Exception {
		return (String[] args) -> {
			// 일반 사용자 5명 등록
			userService.create("aaaa", "1234", "aaaa@naver.com");
			userService.create("bbbb", "1234", "bbbb@naver.com");
			userService.create("cccc", "1234", "cccc@naver.com");
			userService.create("dddd", "1234", "dddd@naver.com");
			userService.create("eeee", "1234", "eeee@naver.com");
		};
	}

}
