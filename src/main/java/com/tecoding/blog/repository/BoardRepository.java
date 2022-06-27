package com.tecoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecoding.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer> {

}
