package com.tecoding.blog.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecoding.blog.model.RoleType;
import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	// UserRepository 메모리에 올라가 있는 상태
	// 그럼 어떻게 가져 오나요? DI
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/dummy/users")
	public List<User> 전체사용자검색() {
		return userRepository.findAll();
	}

	// 페이징 처리
	// http://localhost:9090/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageble) {

//		Page<User> pageUser = userRepository.findAll(pageble);

//		Page page =  userRepository.findAll(pageble);
//		List<User> user =  page.getContent();

		Page<User> pageUser = userRepository.findAll(pageble);
		return pageUser;
	}

	// REST POST
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("===================");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println("===================");

		System.out.println(user.getId());
		System.out.println(user.getCreateDate());
		System.out.println(user.getRole()); // null -> default 값 불가

		user.setRole(RoleType.USER);

		userRepository.save(user);

		return "회원가입 완료 되었습니다";
	}

	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// Optional --> Optional 로 깜싸서 User Entity를 가지고 오겠다
		// 1, 100, 200 --> null
//		User userTemp1 =  userRepository.findById(id).get();
//		User userTemp2 =  userRepository.findById(id).orElseGet(() -> {
//			return new User(); 
//		});
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다 : " + id);
		});

		// User user = userRepository.findById(id);

		return user;
	}

	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {

		System.out.println("id : " + id);
		System.out.println("password : " + reqUser.getPassword());
		System.out.println("email : " + reqUser.getEmail());
		// select 해와서 추가적인 처리
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다 : " + id);
		});

		// --> DB 저장 // username 없다 .
		userEntity.setPassword(reqUser.getPassword());
		userEntity.setEmail(reqUser.getEmail());
		// User user = userRepository.save(userEntity);
		return null;
	}

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "해당 유저는 없습니다";
		}
		return id + " 는 삭제 되었습니다.";
	}

}
