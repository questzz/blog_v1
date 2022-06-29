package com.tecoding.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100)
	private String title;
	@Lob // 대용량 데이터
	private String content;
	@ColumnDefault("0") // int // String " '안녕' "
	private int count; // 조회수
	
	// 여러개의 게시글은 하나의 유저를 가진다.
	// Many == Board, One == User
	@ManyToOne(fetch = FetchType.EAGER) // Board select 한번에 데이터를 가져와  
	@JoinColumn(name = "userId")
	private User userId;
	
	// 댓글정보 
	// 하나에 게시글에 여러개의 댓글이 있을 수 있다.
	// one = board, many = reply 
	// mappedBy = "board" board는 reply 테이블에 필드 이름이다. 
	// mappedBy 는 연관 관계에 주인이 아니다 (FK)  
	// DB 에 컬럼을 만들지 마시오 
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) 
	@JsonIgnoreProperties({"board", "content"}) // Reply 안에 있는 board getter 를 무시해라(호출이 안됨) 
	private List<Reply> replys; 
	
		
	@CreationTimestamp
	private Timestamp createDate;

}
