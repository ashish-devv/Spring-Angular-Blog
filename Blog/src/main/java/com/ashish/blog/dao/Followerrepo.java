package com.ashish.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Follower;

public interface Followerrepo extends JpaRepository<Follower, Integer>{
	
	@Query("select f from Follower f where f.senderid= :sid and f.receiverid= :rid")
	public Follower getBysidAndrid(@Param("sid") int sid,@Param("rid") int rid);

}
