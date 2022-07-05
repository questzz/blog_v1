package com.tecoding.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tecoding.blog.model.Image;
import com.tecoding.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFileDto {
	//private MultipartFile[] file;
	private MultipartFile file;
	private String uuid; // 고유한 파일 이름을 만들기 위한 속성 
	private String stroyText; 
	
	public Image toEntity(String storyImageUrl, User user) {
		return Image.builder()
				.storyText(stroyText)
				.storyImageUrl(storyImageUrl)
				.originFileName(file.getOriginalFilename())
				.user(user)
				.build();
	}
	
}






