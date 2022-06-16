package com.tecoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 200)
	private String content;

	@ManyToOne // 여러개의 댓글은 하나의 게시글에 존재할 수 있다.
	@JoinColumn(name = "boardId")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "userId") // <---- Reply 테이블에 컬럼명이 된다.
	private User user;

	@CreationTimestamp
	private Timestamp createDate;
}
