package com.tecoding.blog.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tecoding.blog.model.User;

import lombok.Data;

@Data
public class PricipalDetail implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;  // 콤포지션 관계 
	
	public PricipalDetail(User user) {
		this.user = user; 
	}
	

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	/**
	 * 계정이 만료되지 않았는지 리턴 한다. 
	 * (true) 만료 안됨 
	 * (false) 계정이 만료된 유저 
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * 
	 *  계정 잠김 여부 확인 
	 *  (true 사용가능)
	 *  (false 사용불가)
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * 	isCredentialsNonExpired
	 *  비밀번호 만료 여부를 알려 준다. 
	 *   true (사용가능)
	 *   false (사용불가) 
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 계정 활성화 여부 
	 * true : 사용가능 
	 * false : 로그인 불가 
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * 
	 * 계정의 권한을 반환 한다 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
//		collectors.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				// "ROLE_" 스프링 시큐리티 규칙 (꼭 넣기) 
//				// "ROLE_USER", "ROLE_ADMIN"
//				return "ROLE_" + user.getRole();
//			}
//		});
		collectors.add(() -> {return "ROLE_" + user.getRole();});
		return collectors;
	}

}
