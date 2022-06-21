package com.tecoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecoding.blog.model.User;

// DAO 
// Bean으로 등록 될 수 있나요? --> 스프링에서  Ioc 에서 객체를 가지고 있나요? 
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// insert 
	// select 
	// update 
	// delete 
}
