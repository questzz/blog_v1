package com.tecoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tecoding.blog.auth.PricipalDetail;
import com.tecoding.blog.dto.RequestFileDto;
import com.tecoding.blog.model.Image;
import com.tecoding.blog.service.StoryService;

@Controller
@RequestMapping("/story")
public class StoryController {

	@Autowired
	StoryService storyService;

	@GetMapping("/home")
	public String storyHome(Model model,
			@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Image> imagePage = storyService.getImageList(pageable);
		System.out.println("===============");
		System.out.println(imagePage.getContent().toString());
		System.out.println("===============");
		model.addAttribute("imagePage", imagePage);
		return "/story/home";
	}

	@GetMapping("/upload")
	public String storyUpload() {
		return "/story/upload";
	}

	@PostMapping("/image/upload")
	public String storyImageUpload(RequestFileDto fileDto, @AuthenticationPrincipal PricipalDetail pricipalDetail) {
		storyService.imgeUpload(fileDto, pricipalDetail.getUser());
		return "redirect:/story/home";
	}

}
