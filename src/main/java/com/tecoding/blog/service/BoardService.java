package com.tecoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecoding.blog.model.Board;
import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void write(Board board, User user) { // title, content 

		board.setCount(0);
		board.setUserId(user);
		boardRepository.save(board);
	}
}







