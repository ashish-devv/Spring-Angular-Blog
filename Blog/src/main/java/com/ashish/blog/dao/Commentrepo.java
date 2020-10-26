package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Comments;

public interface Commentrepo extends JpaRepository<Comments, Integer>{

	@Query("select c from Comments c where c.pid= :pid order by c.commenton desc")
	public List<Comments> getAllCommentbyPostid(@Param("pid") int pid);
	
	@Query("select count(c) from Comments c where c.uid= :uid")
	public int noOfCommentbyUid(@Param("uid") int uid);
	
}
 