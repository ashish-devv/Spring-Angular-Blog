package com.ashish.blog.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		List<Post> list=this.postrepo.FindAllPostByUserId(id);
		
		return list;
	}
	
	@GetMapping("/like/{pid}")
	public String getlikeinfo(@PathVariable("pid") int pid,HttpSession httpSession)
	{
		try {
			Post post=this.postrepo.findByPid(pid);
			if(post!=null)
			{
				return post.toString();
			}
			else
			{
				throw new Exception("No Post by this {Pid} 👎");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}

}
