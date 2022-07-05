package com.tecoding.blog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecoding.blog.dto.RequestFileDto;
import com.tecoding.blog.model.Image;
import com.tecoding.blog.model.User;
import com.tecoding.blog.repository.StoryRepository;

@Service
public class StoryService {
	
	@Value("${file.path}")
	private String uploadFolder; 
	
	@Autowired
	StoryRepository storyRepository;
	
	public Page<Image> getImageList(Pageable pageable) {
		return storyRepository.findAll(pageable);
	}
	
	
	@Transactional
	public void imgeUpload(RequestFileDto fileDto, User user) {
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + "story";
		String newFileName = (imageFileName.trim()).replaceAll("\\s", ""); 
		Path imageFilePath = Paths.get(uploadFolder + newFileName);
		
		try {
			Files.write(imageFilePath, fileDto.getFile().getBytes());
			// DB 저장 
			Image imageEntity =  fileDto.toEntity(newFileName, user);
			storyRepository.save(imageEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
