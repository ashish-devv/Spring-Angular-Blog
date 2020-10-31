package com.ashish.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Forgetpass;

public interface ForgetPassrepo extends JpaRepository<Forgetpass, Integer> {
	
	@Query("select f from Forgetpass f where uniquekey=:uniquekey")
	public Forgetpass checkuser(@Param("uniquekey") int uniquekey);

}
