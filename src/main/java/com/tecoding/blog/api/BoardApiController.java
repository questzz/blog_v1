package com.tecoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tecoding.blog.auth.PricipalDetail;
import com.tecoding.blog.dto.ResponseDto;
import com.tecoding.blog.model.Board;
import com.tecoding.blog.model.Reply;
import com.tecoding.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PricipalDetail detail) {
		
		boardService.write(board, detail.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.deleteById(id); 
		return new ResponseDto<>(HttpStatus.OK.value(), 1);
	} 
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		boardService.modifyBoard(id, board);	
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	 
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@PathVariable int boardId,
			@RequestBody Reply reply, @AuthenticationPrincipal PricipalDetail pricipalDetail) {
		
		// 서비스 데이터 처리 
		boardService.writeReply(pricipalDetail.getUser(), boardId, reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}




