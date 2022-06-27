package com.tecoding.blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.UserRepository;


@Service
public class PricipalDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(() -> {
					return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
				});
		return new PricipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장이 된다. 
	}

}
