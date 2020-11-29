package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Savedlist;

public interface Savedpostrepo extends JpaRepository<Savedlist, Integer>{

	public Savedlist findByPidAndUid(int pid,int uid);
	
	@Query("select s.pid from Savedlist s where s.uid= :uid")
	public List<Integer> findPidByUid(@Param("uid") int uid);
	
}
