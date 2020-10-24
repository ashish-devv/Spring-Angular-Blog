package com.ashish.blog.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.entity.Post;

@RestController
@RequestMapping("/user/api")
public class UserApi {
	
	@Autowired
	Postrepo postrepo;
	
	@GetMapping("/allpost")
	public List<Post> getallpost()
	{
		List<Post> list=this.postrepo.getAllPostInDesc();
		
		return list;
	}
	
	@GetMapping("/allpostbyme")
	public List<Post> getallpostbyloggeduser(HttpSession httpSession)
	{
		int id=(int)httpSession.getAttribute("uid");
		List<Post> list=this.postrepo.FindAllByUserId(id);
		
		return list;
	}

}
