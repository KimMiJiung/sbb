package com.mysite.sbb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	// 로그인 처리
	
	
	// 회원 가입
	public SiteUser create(String username, String password, String email) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		// 비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		this.userRepository.save(user);
		return user;
	}
	
	// 사용자 정보 획득 - username(사용자 아이디)
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if (siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("SiteUser Not Found!!!");
		}
	}
	
	// 사용자 정보 수정
	public void modify(SiteUser user, String password, String email) {
		// 비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		this.userRepository.save(user);
	}
		
	// 사용자 정보 삭제
	public void delete(SiteUser user) {
		this.userRepository.delete(user);
	}
	
}
