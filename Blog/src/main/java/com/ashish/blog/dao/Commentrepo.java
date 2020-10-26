package com.ashish.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashish.blog.entity.Comments;

public interface Commentrepo extends JpaRepository<Comments, Integer>{

}
 