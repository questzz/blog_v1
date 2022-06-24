package com.tecoding.blog.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	
	@Test
	public void hashEncode() {
		String encPassword = new BCryptPasswordEncoder().encode("123");
		System.out.println("해시 값 :" + encPassword);
		
		// $2a$10$A9/sBfe7s9jRPL6ZqHTcqeVYANhfzD/7AHvTlAGfo5767K9a7L.WK
		// $2a$10$DddOJQZpyaHJFl/S9qgDJ.jSmdAEItVFrnbAi/bMsHAajHTAiWc6q
	}
}
