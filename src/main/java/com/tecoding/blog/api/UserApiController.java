package com.tecoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecoding.blog.dto.ResponseDto;
import com.tecoding.blog.model.RoleType;
import com.tecoding.blog.model.User;
import com.tecoding.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	UserService userService;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		// DB ( 벨리데이션) ....
		System.out.println("UserApiController 호출됨!!!");
		user.setRole(RoleType.USER);
		int result = userService.saveUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
}


