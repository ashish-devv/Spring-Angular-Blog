package com.ashish.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Like;

public interface Likerepo extends JpaRepository<Like, Integer>{
	
	@Query("select l from Like l where l.postid= :pid and l.userid= :uid")
	public Like getlikeinfobypostid(@Param("pid") int pid,@Param("uid") int uid);
	
	@Query("select COUNT(l) from Like l where l.postid= :pid")
	public int countBypid(@Param("pid") int pid);
	
	@Query("delete from Like l where l.userid= :userid")
	public void deleteByuserid(@Param("userid") int userid);

}
