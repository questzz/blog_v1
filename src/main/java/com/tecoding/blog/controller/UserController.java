package com.tecoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecoding.blog.dto.OAuthToken;
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

	@GetMapping("/auth/kakao/callback")
	@ResponseBody
	public String kakaoCallback(@RequestParam String code) {

		// HttpsURLConnect ...
		// Retrofit2
		// OkHttp
		// RestTemplate
		RestTemplate rt = new RestTemplate();

		// http 메세지 -> POST

		// 시작줄
		// http header
		// http boady

		// header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// body 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "141b8184eb9a5187f47d408f956643c0");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);

		// 헤더와 바디를 하나의 오브젝트로 담아야 한다.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청 - post 방식 - 응답
		ResponseEntity<String> response =
				rt.exchange("https://kauth.kakao.com/oauth/token",
						HttpMethod.POST,
				kakaoTokenRequest, String.class);
		
		// response -> Object 타입으로 변환 (Gson, Json Simple, ObjectMapper)
		// 파싱 처리 
		OAuthToken authToken = null;
		ObjectMapper objectMapper = new ObjectMapper();
		// String ---> Object (클래스 생성)
		try {
			authToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 액세스 토큰 사용 
		RestTemplate rt2 = new RestTemplate(); 
		
		HttpHeaders headers2 = new HttpHeaders();
		// 주의 Bearer 다음에 무조건 한 칸 띄우기 !!! 
		headers2.add("Authorization", "Bearer " + authToken.getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디 
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class);
		
		System.out.println(response2);
		
		// 단 위에 했던 방식으로 진행 (KakaoProfile.class)
		// 요구 조건 (카멜 케이스로 파싱해주세요) 
		
		// POST --->
		// 통신 -- 인증서버
		return "카카오 프로필 정보 요청 :" + response2;
	}

}
