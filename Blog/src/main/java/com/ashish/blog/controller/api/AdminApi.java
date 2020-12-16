package com.ashish.blog.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.blog.dao.Commentrepo;
import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.dao.Reportrepo;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.Post;
import com.ashish.blog.entity.User;

@RestController
@RequestMapping("/admin")
public class AdminApi {
	
	
	
	@Autowired
	Postrepo postrepo;
	
	@Autowired
	Userrepo userrepo;
	
	@Autowired
	Commentrepo commentrepo;
	
	@Autowired
	Reportrepo reportrepo;
	
	
	@RequestMapping("/deleteaccount/{uid}")
	public ResponseEntity<?> deleteaccount(@PathVariable("uid") int profileid)
	{
		try {
			this.userrepo.deleteById(profileid);
			return new ResponseEntity<>(profileid,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/deletepost/{pid}")
	public ResponseEntity<?> deletepost(@PathVariable("pid") int postid)
	{
		try {
			this.postrepo.deleteById(postid);
			return new ResponseEntity<>(postid,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/dashboard")
	public ResponseEntity<?> dashboard()
	{
		try {
			long usercount=this.userrepo.count();
			long postcount=this.postrepo.count();
			long commentcount=this.commentrepo.count();
			List<Long> list=new ArrayList<Long>();
			list.add(usercount);
			list.add(postcount);
			list.add(commentcount);
			
			return new ResponseEntity<>(list,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping("/listallusers")
	public ResponseEntity<?> listallusers()
	{
		try {
			List<User> l=this.userrepo.findAll();
			return ResponseEntity.ok(l);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/blockuser/{uid}")
	public ResponseEntity<?> blockuser(@PathVariable("uid") int uid)
	{
		try {
			User u=this.userrepo.getUserByUid(uid);
			if(u==null)
			{
				throw new Exception("No User Found With "+uid);
			}
			else 
			{
				if(u.isEnabled())
				{
					u.setEnabled(false);
				}
				else
				{
					u.setEnabled(true);
				}
				this.userrepo.save(u);
			}
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/getreportedpost")
	public ResponseEntity<?> getreportedpost()
	{
		try {
			List<Integer> listofreported=this.reportrepo.FindAll();
			List<Post> listofpost=this.postrepo.FindByPidIn(listofreported);
			return ResponseEntity.ok(listofpost);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/deletereportedpost/{pid}")
	public ResponseEntity<?> deletereportedpost(@PathVariable("pid") int pid)
	{
		try {
			this.postrepo.deleteById(pid);
			return ResponseEntity.ok("Deleted SuccessFully");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/removefromreportedlist/{pid}")
	public ResponseEntity<?> removefromreportedlist(@PathVariable("pid") int pid)
	{
		try {
			this.reportrepo.deletefromreportedlist(pid);
			return ResponseEntity.ok("removed SuccessFully");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
		}
	}
	
}
