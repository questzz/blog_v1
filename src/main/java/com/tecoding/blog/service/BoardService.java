package com.tecoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecoding.blog.model.Board;
import com.tecoding.blog.model.Reply;
import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.BoardRepository;
import com.tecoding.blog.repository.ReplyRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	
	@Transactional
	public void write(Board board, User user) { // title, content 

		board.setCount(0);
		board.setUserId(user);
		boardRepository.save(board);
	}
	
	@Transactional
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public Board boardDetail(int boardId) {
		return boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("해당 글은 찾을 수 없습니다.");
		});
	}
	
	@Transactional
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}
	
	// boardService.modifyBoard(id, board);
	@Transactional
	public void modifyBoard(int id, Board board) { // title, content 
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 글은 찾을 수 없습니다.");
		});
		
		boardEntity.setTitle(board.getTitle());
		boardEntity.setContent(board.getContent());
		// 더티체킹 
	}
	
	// boardService.writeReply(pricipalDetail.getUser(), boardId, reply);
	@Transactional
	public void writeReply(User user, int boardId, Reply requestReply) {
		Board boardEntity =  boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글이 존재 하지 않아요~");
		});
		requestReply.setUser(user);
		requestReply.setBoard(boardEntity);
		replyRepository.save(requestReply);
	}
	
}







