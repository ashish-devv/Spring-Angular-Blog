package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Post;

public interface Postrepo extends JpaRepository<Post, Integer>{
	@Query("select p from Post p order by p.postedon desc")
	public List<Post> getAllPostInDesc();
	
	@Query("select p from Post p where p.id = :id order by p.pid desc")
	public List<Post> FindAllByUserId(@Param("id") int id);
}
