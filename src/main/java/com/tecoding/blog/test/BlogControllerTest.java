package com.tecoding.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecoding.blog.model.Board;


/**
 * 
 * 스프링이 com.teconding.blog 패키지 이하를 스캔해서 모든 자파 파일을 
 * 메모리에 new 하는것은 아니고 특정 어노테이션이 붙어 있는 파일들을 
 * new 해서 (IoC) 스프링 컨테이너에서 관리해준다. 
 *
 */
@Controller
@RequestMapping("/test")
public class BlogControllerTest {
	
	@GetMapping("/hello")
	public String hello() {
		
		
		return "<h1>hello spring boot</h1>";
	}
	
	@GetMapping("/temp1")
	public String temp1() {
		
		return "test";
	}
	
	
	// key=value title="xxx"&content="xxxx";
	@GetMapping("/xss")
	@ResponseBody
	public String xssTest(Board board) {
		
		System.out.println("board : " + board);
		
		return "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "<meta charset=\"UTF-8\">\r\n"
				+ "<title>Insert title here</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "	<h1>xss lucy 라이브러리 확인</h1>\r\n"
				+ "</body>\r\n"
				+ "</html>";
	}
}
