package com.mysite.sbb.controller;

import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.form.AnswerForm;
import com.mysite.sbb.form.CommentForm;
import com.mysite.sbb.form.QuestionForm;
import com.mysite.sbb.service.QuestionService;
import com.mysite.sbb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	// 스프링부트에서 권장하는 빈의 등록 방법
	private final QuestionService questionService;
	private final UserService userService;

	// 글 전체 목록 -> 페이징 처리를 하지 않음, 검색 기능이 없음.
	/*
	@GetMapping("/list")
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question/question_list";
	}
	*/

	// 글 전체 목록 -> 페이징 처리함. 검색 기능은 없음.
	/*
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question/question_list";
	}
	*/

	/*
	// 글 전체 목록 -> 페이징 처리함. 검색 기능을 추가
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="kw", defaultValue="") String kw) {
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "question/question_list";
	}
	*/
	
	// 글 전체 목록 -> 페이징 처리함. 검색 기능을 추가. 카테고리별 검색 추가
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="kw", defaultValue="") String kw, @RequestParam(value="searchType", defaultValue="") String searchType) {
		Page<Question> paging = this.questionService.getList(page, kw, searchType);
		
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("searchType", searchType);
		return "question/question_list";
	}	

	// 상세 보기
	@GetMapping("/detail/{id}")
	public String detail(Model model, @RequestParam(value="page", defaultValue="0") int page,  @PathVariable("id") Long id
			, AnswerForm answerForm
			, CommentForm commentForm) {
		Question question = this.questionService.getQuestion(id);
		Page<Answer> answerPage = this.questionService.getAnswerPage(page, question.getAnswerList());

		model.addAttribute("question", question);
		model.addAttribute("paging", answerPage);
		return "question/question_detail";
	}

	// 질문 등록 폼으로 이동
	// - 로그인한 사용자만 이 메서드를 사용할 수 있음, 로그인을 하지 않은 사용자는 로그인 페이지로 강제로 이동함.
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question/question_form";
	}
	
	// 질문 등록 처리
	// @PreAuthorize : 메서드를 실행하기 전에 하는 권한검사
	// isAuthenticated():  인증된 사용자만 해당 메서드 접근할 수 있도록 제한한 애너테이션,  현재 사용자가 익명이 아니라면 (로그인 상태라면) true
	@PreAuthorize("isAuthenticated()")	
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, 
			Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question/question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}

	// 질문 수정 폼으로 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Long id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		
		// 질문 작성자(Question의 username)와 로그인 사용자(Principal)가 같은지를 비교하여 예외 처리
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		// 질문 작성자와 로그인 사용자가 같을 때 
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question/question_form";	
	}
	
	// 질문 수정 처리
	@PreAuthorize("isAuthenticated()")	
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
			@PathVariable("id") Long id, Principal principal) {			
		if (bindingResult.hasErrors()) {
			return "question/question_form";	
		}
		
		Question question = this.questionService.getQuestion(id);
		
		// 질문 작성자(Question의 username)와 로그인 사용자(Principal)가 같은지를 비교하여 예외 처리
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());		
		return "redirect:/question/detail/" + id;
	}
	
	// 질문 삭제 처리
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Long id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		// 질문 작성자(Question의 username)와 로그인 사용자(Principal)가 같은지를 비교하여 예외 처리
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	// 추천 추가 처리
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(@PathVariable("id") Long id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return "redirect:/question/detail/" + id;
	}	
}
