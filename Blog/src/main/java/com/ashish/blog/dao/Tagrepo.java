package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashish.blog.entity.Tag;

public interface Tagrepo extends JpaRepository<Tag, Integer>{

	@Query("select t from Tag t ")
	List<Tag> FindAllTag();
	
}
