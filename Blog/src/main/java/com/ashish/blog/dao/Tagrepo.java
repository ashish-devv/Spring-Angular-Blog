package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Tag;

public interface Tagrepo extends JpaRepository<Tag, Integer>{

	@Query("select t from Tag t ")
	List<Tag> FindAllTag();
	
	public List<Tag> findByTagbyuid(int uid);
	
	@Query("select t from Tag t where t.tag_id= :id")
	public Tag findByTagid(@Param("id") int id);
	
}
