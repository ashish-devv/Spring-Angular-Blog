package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Reported;

public interface Reportrepo extends JpaRepository<Reported, Integer> {

	public Reported findByPid(int pid);
	
	@Query("select r.pid from Reported r")
	public List<Integer> FindAll();
	
	@Query("delete from Reported r where r.pid=:pid")
	public void deletefromreportedlist(@Param("pid") int pid);
	
}
