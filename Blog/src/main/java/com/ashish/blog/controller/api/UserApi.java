package com.ashish.blog.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
					return "done";
				}
				else
				{
					this.likerepo.deleteById(l.getLid());
					return "done";
				}
				
			}
			else
			{
				throw new Exception("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	
	@GetMapping("/likecount/{pid}")
	public Map<String,Integer> getlikecount(@PathVariable("pid") int pid,HttpSession httpSession)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			int count=this.likerepo.countBypid(pid);
			HashMap<String, Integer> resp = new HashMap<>();
			resp.put("count", count);
			Like l=this.likerepo.getlikeinfobypostid(pid,uid);
			if(l==null)
			{
				resp.put("likedornot",0);
			}
			else
			{
				resp.put("likedornot",1);
			}
			return resp;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			HashMap<String, Integer> resp = new HashMap<>();
			resp.put("error", 1);
			return resp;
		}
	}

}
