package com.ashish.blog.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.blog.dao.Followerrepo;
import com.ashish.blog.dao.Likerepo;
import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.dao.Tagrepo;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.Follower;
import com.ashish.blog.entity.Like;
import com.ashish.blog.entity.Post;
import com.ashish.blog.entity.Tag;
import com.ashish.blog.entity.User;

@RestController
@RequestMapping("/user/api")
public class UserApi {
	
	@Autowired
	Postrepo postrepo;
	
	@Autowired
	Likerepo likerepo;
	
	@Autowired
	Followerrepo  followerrepo;
	
	@Autowired
	Tagrepo tagrepo;
	
	@Autowired
	Userrepo userrepo;
	
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
	
	@GetMapping("/follow/{userid}")
	public void followfunction(@PathVariable("userid") int userid,HttpSession httpSession)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			User user=this.userrepo.getUserByUid(userid);
			if(user!=null)
			{
				Follower follower=this.followerrepo.getBysidAndrid(uid, userid);
				if(follower==null)
				{
					Follower f=new Follower();
					f.setReceiverid(userid);
					f.setSenderid(uid);
					this.followerrepo.save(f);
					System.out.println("followed👍");
				}
				else
				{
					this.followerrepo.deleteById(follower.getFid());
					System.out.println("Unfollowed👎");
				}
			}
			else
			{
				throw new Exception(" 404 : No user Found !");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/followedornot/{userid}")
	public Map<String,Integer> followedornot(@PathVariable("userid") int userid,HttpSession httpSession)
	{
		HashMap<String, Integer> resp = new HashMap<>();
		try {
			int uid=(int) httpSession.getAttribute("uid");
			User user=this.userrepo.getUserByUid(userid);
			if(user!=null)
			{
				Follower follower=this.followerrepo.getBysidAndrid(uid, userid);
				
				if(follower==null)
				{
					
					resp.put("followed", 0);
				}
				else
				{
					resp.put("followed", 1);
				}
				return resp;
			}
			else 
			{
				throw new Exception(" 404 : No user Found !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("error", 1);
			return resp;
		}
	}
	
	@GetMapping("/searchpost/{keyword}")
	public List<Post> searchpost(@PathVariable("keyword") String keyword)
	{
		List<Post> list;
		if(keyword.equals("")||keyword.equals(" "))
		{
			list=null;
		}
		else
		{
			list= this.postrepo.findByPostHeadingContainingAndPostStatusIs(keyword,true);
		}
		
		return list;
	}
	
	@GetMapping("/searchuser/{keyword}")
	public ResponseEntity<?> searchuser(@PathVariable("keyword") String keyword)
	{
		List<User> list;
		if(keyword.equals("")||keyword.equals(" "))
		{
			list=null;
		}
		else
		{
			list=this.userrepo.findByNameContainingAndRoleIs(keyword, "ROLE_USER");
//			for (User temp : list) {
//				temp.setPassword(null);
//	            System.out.println(temp);
//	        }
			//System.out.println(list);
		}
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/tag")
	public ResponseEntity<?> tagbyuser(HttpSession httpSession)
	{
		try {
			
			List<Tag> list=this.tagrepo.findByTagbyuid((int) httpSession.getAttribute("uid"));
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok("Error Occured");
		}
	}
	
	
	@GetMapping("/postbytag/{tagname}")
	public ResponseEntity<?> postbytag(@PathVariable("tagname") String tagname)
	{
		try {
			List<Post> list = this.postrepo.findByPostTagAndPostStatusTrue(tagname);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok("Error Occured");
		}
	}
}
