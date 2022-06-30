package com.tecoding.blog.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tecoding.blog.model.Board;
import com.tecoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	
	@Autowired
	BoardService boardService;
	
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size = 5 , 
				sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Board> pageBoards =  boardService.getBoardList(pageable);
		
		// [ 1 2 3 4 5 '6' 7 8 9 10]
		
		// 1. 현재 페이지 앞 뒤로 2 블록(칸)씩 보여야 한다.
		// 2. 페이지 버튼을 누르면(블록) 해당 페이지로 화면을 이동해야 한다.
		// 3. 현재 페이지에 'active' 하기 
		  
		
//		int startPage = pageBoards.getPageable().getPageNumber() - 2;
//		int endPage = pageBoards.getPageable().getPageNumber() + 2;
		
		int nowPage = pageBoards.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1); // 두 int 값 중에 큰 값을 반환 합니다. 
		int endPage = Math.min(nowPage + 2, pageBoards.getTotalPages());
		System.out.println("------------------------");
		log.info("현재 화면의 블록 숫자(현재 페이지) : {} ", nowPage);
		log.info("현재 화면의 보여질 블록의 시작 번호 : {} ", startPage);
		log.info("현재 화면의 보여질 마지막 블록의 번호 : {} ", endPage);
		log.info("화면에 보여줄 총 게시글 / 한 화면에 보여질 게시 글 (총 페이지 숫자)  : {} ", pageBoards.getTotalPages());
		System.out.println("------------------------");
		
		
			
		// 페이지 번호를 배열로 만들어서 던져 주기 !!! 
		ArrayList<Integer> pageNumbers = new ArrayList<>();
		// 주의 !!! (마지막 번호까지 저장하기)
		for (int i = startPage; i <= endPage; i++) {
			pageNumbers.add(i);
		}
		
		
		// 시작 페이지를 설정해야 한다. 
		
		model.addAttribute("pageable", pageBoards);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageNumbers", pageNumbers);
		
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
