package com.ashish.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashish.blog.entity.Follower;

public interface Followerrepo extends JpaRepository<Follower, Integer>{
	
	@Query("select f from Follower f where f.senderid= :sid and f.receiverid= :rid")
	public Follower getBysidAndrid(@Param("sid") int sid,@Param("rid") int rid);
	
	
	@Query("select f.receiverid from Follower f where f.senderid= :senderid ")
	public List<Integer> findReceiveridBySenderid(@Param("senderid")int senderid);
	
	@Query("select f.senderid from Follower f where f.receiverid= :receiverid ")
	public List<Integer> findSenderidByReceiverid(@Param("receiverid")int receiverid);
	
	public Long countByReceiverid(int receiverid);

}
