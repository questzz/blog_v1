package com.tecoding.blog.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.UserRepository;

@Service // (IoC등록) 
public class UserService {
	
	// DI 의존 주입 
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public int saveUser(User user) {
		try {
			userRepository.save(user);
			return 1; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 
	}
	
	@Transactional(readOnly = true)
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
}





