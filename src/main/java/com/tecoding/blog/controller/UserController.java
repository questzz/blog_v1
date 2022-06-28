package com.tecoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tecoding.blog.dto.ResponseDto;
import com.tecoding.blog.model.User;
import com.tecoding.blog.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserService userService;
	
	// .../blog/user/login_form
	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}
	
	@GetMapping("/auth/join_form")
	public String joinForm() {
		return "user/join_form";
	}
	
	@GetMapping("/logout")
	public String logout() {
		// 세션정보를 제거 (로그아웃처리) 
		httpSession.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/user/update_form")
	public String updateForm() {
		return "user/update_form";
	}
	
	@PostMapping("/auth/joinProc")
	// 기본 데이터 파싱 전략 key=value
	// application/x-www-form-urlencoded;charset=UTF-8 // key=value
	public String save(User user) { // JSON
		int result = userService.saveUser(user);
		return "redirect:/";
	}
	
}
