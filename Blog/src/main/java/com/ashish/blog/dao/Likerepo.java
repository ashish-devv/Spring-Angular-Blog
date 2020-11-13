package com.ashish.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashish.blog.entity.Like;

public interface Likerepo extends JpaRepository<Like, Integer>{

}
