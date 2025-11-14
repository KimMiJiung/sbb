package com.mysite.sbb.controller;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.form.SiteUserForm;
import com.mysite.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;

  
	// 로그인 폼
	@GetMapping("/login")
	public String login() {
		return "user/login_form";
	}

	// 회원가입 폼
	@GetMapping("/signup")
	public String signup(SiteUserForm siteUserForm) {
		return "user/signup_form";
	}

	// 회원가입 처리
	@PostMapping("/signup")
	public String signup(@Valid SiteUserForm siteUserForm, BindingResult bindingResult ) {
		// 입력 항목 검증에서 문제가 발생한다면
		if (bindingResult.hasErrors()) {
			return "user/signup_form";
		}

		// 2개의 비밀번호 일치를 확인
		if (!siteUserForm.getPassword1().equals(siteUserForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "user/signup_form";
		}
		
		// 사용자 ID (username)이 이미 존재할 때 
		try {
			userService.create(siteUserForm.getUsername(), siteUserForm.getPassword1(), siteUserForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signUpFailed", "이미 등록된 사용자입니다.");
			return "user/signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signUpFailed", e.getMessage());
			return "user/signup_form";
		}
		
		return "redirect:/";
	}
	
	// 회원 정보 수정
	// id로 password와 email을 수정
	@GetMapping("/modify/{username}")
	public String modifyUser(SiteUserForm siteUserForm, @PathVariable("username") String username, Principal principal) {
		SiteUser siteUser = this.userService.getUser(username);
		
		if (!username.equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원정보 수정 권한이 없습니다.");
		}
		
		siteUserForm.setUsername(username);
		siteUserForm.setPassword1(siteUser.getPassword());
		siteUserForm.setPassword2(siteUser.getPassword());
		siteUserForm.setEmail(siteUser.getEmail());
		
		return "user/modify_user_form";
	}
	
	// 회원정보 수정/삭제 처리
	@PostMapping("/modify/{username}")
	public String modifyUser(@Valid SiteUserForm siteUserForm, BindingResult bindingResult, @RequestParam("mode") String mode, 
				@PathVariable("username") String username, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "user/modify_user_form";
		}
		
		// 2개의 비밀번호 일치를 확인
		if (!siteUserForm.getPassword1().equals(siteUserForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "user/modify_user_form";
		}
		
		SiteUser siteUser = this.userService.getUser(username);
		if (!username.equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원정보 수정 권한이 없습니다.");
		}
		
		if ("update".equals(mode)) {
			this.userService.modify(siteUser, siteUserForm.getPassword1(), siteUserForm.getEmail());
		} else if ("delete".equals(mode)) {
			// foreign로 연결된 값(Answer, Question, SiteUser, voter_id 등록된 유저아이디)이 전부 삭제가 되어야 유저가 삭제됨
			this.userService.delete(siteUser);
		}
		
		return "redirect:/";
	}

}
