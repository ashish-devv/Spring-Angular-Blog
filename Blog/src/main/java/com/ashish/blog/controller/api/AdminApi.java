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
import com.ashish.blog.dao.Userrepo;

@RestController
@RequestMapping("/admin")
public class AdminApi {
	
	
	
	@Autowired
	Postrepo postrepo;
	
	@Autowired
	Userrepo userrepo;
	
	@Autowired
	Commentrepo commentrepo;
	
	
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
	
	
	
	
}
