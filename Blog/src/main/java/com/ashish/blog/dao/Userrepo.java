package com.ashish.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.User;

public interface Userrepo extends JpaRepository<User, Integer> {
	
	@Query("select u from User u  where u.email= :email")
	public User getUserByusername(@Param("email") String email);
	
	@Query("select u from User u  where u.uid= :uid")
	public User getUserByUid(@Param("uid") int uid);

}
