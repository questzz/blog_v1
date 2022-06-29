package com.tecoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스가 자동으로 MySQL 에 테이블을 생성한다.
//@DynamicInsert
public class User {
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라가겠다.
	private int id;
	@Column(nullable = false, length = 100, unique = true)
	private String username;
	@Column(nullable = false, length = 100)
	private String password;
	@Column(nullable = false, length = 50)
	private String email;

	// DOMIN -- 데이터의 범주화
//	@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum 타입 사용 권장 : admin, user, manager

	private String oauth; // kakao, google, naver  ... 
	
	@CreationTimestamp // 시간이 자동으로 입력 된다.
	private Timestamp createDate;
}
