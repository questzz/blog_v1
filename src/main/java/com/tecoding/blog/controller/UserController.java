package com.tecoding.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import com.tecoding.blog.dto.KakaoProfile;
import com.tecoding.blog.dto.KakaoProfile.KakaoAccount;
import com.tecoding.blog.dto.OAuthToken;
import com.tecoding.blog.model.User;
import com.tecoding.blog.service.UserService;

@Controller
public class UserController {
   
	@Value("${tenco.key}")
	private String tencoKey; 
	
	@Autowired
	AuthenticationManager authenticationManager;
	
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

	// securiry에 맡기지 말고 직접 처리해 보자.
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
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
//	@ResponseBody
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
		
		ResponseEntity<KakaoProfile> kakaoProfileResponse = rt2.exchange("https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				KakaoProfile.class);
		
		// 소셜로인 처리 --> 
		// 사용자가 로그인 했을 경우 최초 사용자라면
		// -> 회원가입 처리 한다.
		// -> 한번이라도 가입 진행이 된 사용자면 로그인 처리를 해주면 된다. 
		// -> 만약 회원 가입시 필요한 정보 더 있어야 된다면 추가로 사용자 한테 정보를 받아서 가입 진행 처리를 
		// 해야 한다. 
		
		KakaoAccount account = kakaoProfileResponse.getBody().getKakaoAccount();
		
		System.out.println("카카오 아이디 : " + kakaoProfileResponse.getBody().getId());
		System.out.println("카카오 이메일 : " + account.getEmail());
		
		System.out.println("블로그에서 사용 될 유저네임 " + account.getEmail() + "_" +  kakaoProfileResponse.getBody().getId());
		System.out.println("블로그에서 사용 될 이메일 " + account.getEmail());
		
		User kakaUser = User.builder()
				.username(account.getEmail() + "_" +  kakaoProfileResponse.getBody().getId())
				.password(tencoKey)
				.email(account.getEmail())
				.oauth("kakao")
				.build();
		
		System.out.println(kakaUser);
		
		// 1. UserService 호출해서 저장 진행 
		// 단, 소셜 로그인 요청자가 이미 가입된 유저라면 저장(x) 
		
		User originUser = userService.searchUser(kakaUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("신규 회원이 아니기 때문에 회원가입을 진행");
			userService.saveUser(kakaUser);
		}
		
		// 자동 로그인 처리 -> 시큐리티 세션에다가 강제 저장   
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaUser.getUsername(), tencoKey));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}

}
