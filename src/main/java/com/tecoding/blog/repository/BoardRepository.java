package com.tecoding.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tecoding.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	// SELECT * FROM board WHERE title LIKE '%a%'
	Page<Board> findByTitleContaining(String title, Pageable pageable);

}
