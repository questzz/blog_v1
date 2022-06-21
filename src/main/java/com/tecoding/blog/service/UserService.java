package com.tecoding.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.UserRepository;

@Service // (IoC등록) 
public class UserService {
	
	/**
	 *  
	 *  서비스가 필요한 이유
	 *  
	 *  트랜잭션 관리
	 *  송금 : 홍길동, 이순신 
	 *  홍길동(10), 이순신(0) --> 홍길동 (select) --> 이순신(5) (insert)
	 *  하나의 기능 + 하나의 기능을 묶어서 단위의 기능을 만들어 내기 위해 필요하다. 
	 *  하나의 기능 (서비가 될 수 있다) 
	 *  
	 */
	
	// DI 의존 주입 
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public int saveUser(User user) {
		// select 
		// update
		// insert
		try {
			userRepository.save(user);
			return 1; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 
	}
	
}
