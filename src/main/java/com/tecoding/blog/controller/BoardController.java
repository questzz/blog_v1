package com.tecoding.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tecoding.blog.model.Board;
import com.tecoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	
	@Autowired
	BoardService boardService;
	
	@GetMapping({"", "/", "/board/search"})
	public String index(String q,  Model model, @PageableDefault(size = 5 , 
				sort = "id", direction = Direction.DESC) Pageable pageable) {
		
		String searchTitle = q == null ? "" : q;
		Page<Board> pageBoards =  boardService.searchBoardByTitle(searchTitle, pageable);

		int nowPage = pageBoards.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1); // 두 int 값 중에 큰 값을 반환 합니다. 
		int endPage = Math.min(nowPage + 2, pageBoards.getTotalPages());
		
		// 페이지 번호를 배열로 만들어서 던져 주기 !!! 
		ArrayList<Integer> pageNumbers = new ArrayList<>();
		// 주의 !!! (마지막 번호까지 저장하기)
		for (int i = startPage; i <= endPage; i++) {
			pageNumbers.add(i);
		}
		
		model.addAttribute("pageable", pageBoards);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageNumbers", pageNumbers);
		model.addAttribute("searchTitle", searchTitle);
		
		return "index";
	}
	
	
	@GetMapping("/board/save_form")
	public String saveForm() {
		log.info("saveForm 메서드 호출");
		return "/board/save_form";
	}
	
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));	
		return "/board/detail";
	}
	
	@GetMapping("/board/{id}/update_form")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));	
		return "/board/update_form";
	}
	

}
