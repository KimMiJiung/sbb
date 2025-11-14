package com.mysite.sbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 스프링 시큐리티 환경 설정 클래스
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// -/**: 모든 URL을 허용
	
	// 허용하는 로그인 페이지를 지정하고, 성공했을 때 이동 페이지를 지정
	// formLogin.loginPage("/user/login").defaultSuccessUrl("/"): 
	
	// 로그아웃을 하는 URL을 지정하고, 성공했을 때 이동 페이지를 지정, 세션을 무효화(삭제) 함
	//.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true));
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHtpRequests) -> authorizeHtpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.formLogin((formLogin) -> formLogin.loginPage("/user/login").defaultSuccessUrl("/"))
			.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
					.logoutSuccessUrl("/").invalidateHttpSession(true));
		return http.build();
	}
	
	// 암호화 메서드
	// BCryptPasswordEncoder: 암호화 객체
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 스프링 시큐리티의 인증을 처리하는 메서드
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
		throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
