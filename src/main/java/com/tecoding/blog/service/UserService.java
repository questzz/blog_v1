package com.tecoding.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecoding.blog.model.RoleType;
import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.UserRepository;

@Service // (IoC등록) 
public class UserService {
	
	// DI 의존 주입 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int saveUser(User user) {
		try {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1; 
	}
	
	@Transactional
	public void updateUser(User user) {
		
		// 카카오가 수정이 들어 오면 무시 
		// 기존 수정이 들어오면 처리 
		
		
		User userEntity = userRepository.findById(user.getId())
				.orElseThrow(() -> {
					return new IllegalArgumentException("회원 정보가 없습니다.");
				});
		
		if(userEntity.getOauth() == null || userEntity.getOauth() == "") {
			String rawPasswrod = user.getPassword(); 
			String hashPassword = encoder.encode(rawPasswrod);
			userEntity.setPassword(hashPassword);
			userEntity.setEmail(user.getEmail());
		}
	}
	
	@Transactional(readOnly = true)
	public User searchUser(String username) {
		User userEntity = userRepository.findByUsername(username).orElseGet(() -> {
			return new User(); 
		});
		return userEntity;
	}
	
	
	
//	@Transactional(readOnly = true)
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
}





