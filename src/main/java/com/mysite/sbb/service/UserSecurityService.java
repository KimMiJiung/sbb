package com.mysite.sbb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.repository.UserRepository;
import com.mysite.sbb.role.UserRole;

import lombok.RequiredArgsConstructor;

// 로그인, 권한의 부여를 처리하는 서비스 클래스
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
		
		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		SiteUser siteUser = _siteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		// ADMIN과 일반 USER를 구분하여 구너한을 부여
		if (username.equals("admin")) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
	
}
