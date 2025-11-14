package com.mysite.sbb.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.form.AnswerForm;
import com.mysite.sbb.service.AnswerService;
import com.mysite.sbb.service.QuestionService;
import com.mysite.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	// 빈을 등록
	private final AnswerService answerService;
	private final QuestionService questionService;
	private final UserService userService;
	
	// 답변 등록
	@PreAuthorize("isAuthenticated()")	
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Long id, 
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question/question_detail";
		}
		
		Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
		return "redirect:/question/detail/" + id + "#answer_" + answer.getId();
	}
	
	// 답변 수정 폼으로 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyAnswer(AnswerForm answerForm, @PathVariable("id") Long id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		// 답변 작성자(Answer의 username)와 로그인 사용자(Principal)가 같은지를 비교하여 예외 처리
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "답변 수정 권한이 없습니다.");
		}
		
		answerForm.setContent(answer.getContent());
		return "answer/answer_form";
	}
	
	// 답변 수정 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult, 
			@PathVariable("id") Long id, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			return "answer/answer_form";
		}
		
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "답변 수정 권한이 없습니다.");
		}
		
		this.answerService.modify(answer, answerForm.getContent());		
		return "redirect:/question/detail/"+ answer.getQuestion().getId() + "#answer_" + answer.getId();
	}
	
	// 답변 삭제 처리
	@PreAuthorize("isAuthenticated()")	
	@GetMapping("/delete/{id}")
	public String deleteAnswer(@PathVariable("id") Long id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "답변 삭제 권한이 없습니다.");
		}				
		this.answerService.delete(answer);
		return "redirect:/question/detail/" + answer.getQuestion().getId();
	}

	// 추천 추가
	@PreAuthorize("isAuthenticated()")	
	@GetMapping("/vote/{id}")
	public String voteAnswer(@PathVariable("id") Long id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);
		return "redirect:/question/detail/" + answer.getQuestion().getId() + "#answer_" + answer.getId();
	}
}
