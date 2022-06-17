package com.tecoding.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;
/**
 * 
 * 비식별 관계 
 *
 */
@Embeddable
@NoArgsConstructor
public class RoleId implements Serializable  {
	
	@Column(length = 30)
	private String title; 
	
	@Column(length = 20)
	private String actorName;
	
	
	
	
}
