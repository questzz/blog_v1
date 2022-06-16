package com.tecoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecoding.blog.model.RoleType;
import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	// UserRepository 메모리에 올라가 있는 상태 
	// 그럼 어떻게 가져 오나요? DI 
	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * 
	 *  ===================
		 tenco
		 1234
		 a@naver.com
		 ===================
		 0
		 null
		 null
	 * @return
	 */
	// REST POST 
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("===================");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println("===================");
		
		System.out.println(user.getId());
		System.out.println(user.getCreateDate());
		System.out.println(user.getRole()); // null -> default  값 불가 
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		 
		
		
		return "회원가입 완료 되었습니다"; 
	}	
	
	
	
}
