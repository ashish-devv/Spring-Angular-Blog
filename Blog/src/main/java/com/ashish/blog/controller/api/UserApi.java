package com.ashish.blog.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.blog.dao.Likerepo;
import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.entity.Like;
import com.ashish.blog.entity.Post;

@RestController
@RequestMapping("/user/api")
public class UserApi {
	
	@Autowired
	Postrepo postrepo;
	
	@Autowired
	Likerepo likerepo;
	
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
			int uid=(int) httpSession.getAttribute("uid");
			Post post=this.postrepo.findByPid(pid);
			if(post!=null)
			{
				Like l=this.likerepo.getlikeinfobypostid(pid,uid);
				if(l==null)
				{
					Like newlike = new Like();
					newlike.setPostid(pid);
					newlike.setUserid(uid);
					//System.out.println(newlike);
					this.likerepo.save(newlike);
					int count = this.likerepo.countBypid(pid);
					return count+"<a class='btn btn-danger'href='/user/api/like/"+pid+"'>👎</a>";
				}
				else
				{
					this.likerepo.deleteById(l.getLid());
					int count = this.likerepo.countBypid(pid);
					return count+"<a class='btn btn-success'href='/user/api/like/"+pid+"'>👍</a>";
				}
				
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
	
	
	@GetMapping("/likecount/{pid}")
	public String getlikecount(@PathVariable("pid") int pid)
	{
		try {
			int count=this.likerepo.countBypid(pid);
			return ""+count;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
