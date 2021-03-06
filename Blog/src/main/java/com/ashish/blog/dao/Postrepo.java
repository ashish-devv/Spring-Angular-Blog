package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Post;

public interface Postrepo extends JpaRepository<Post, Integer>{
	@Query("select p from Post p where p.postStatus=true order by p.postedon desc")
	public List<Post> getAllPostInDesc();
	
	@Query("select p from Post p where p.id = :id order by p.pid desc")
	public List<Post> FindAllPostByUserId(@Param("id") int id);
	
	@Query("select p from Post p where p.pid= :pid")
	public Post findByPid(@Param("pid") int pid);
	
	@Query("select count(p) from Post p where p.id= :id")
	public int findNoofPostByid(@Param("id") int id);
	
	public List<Post> findByPostHeadingContainingAndPostStatusIs(String postHeading,boolean PostStatus);
	
	@Query("select count(p) from Post p where p.postTag= :tagname")
	public int findnoofpostbytag(@Param("tagname") String tagname);
	
	public List<Post> findByPostTagAndPostStatusTrue(String tag);
	
	@Query("select p from Post p where p.pid in :list")
	public List<Post> FindByPidIn(@Param("list") List<Integer> l);
	
	@Query("select p from Post p where p.id in :list order by p.pid desc")
	public List<Post> FindByIdIn(@Param("list") List<Integer> l);
}
