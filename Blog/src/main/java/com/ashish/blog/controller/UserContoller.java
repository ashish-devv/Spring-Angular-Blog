package com.ashish.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.User;

@Controller
@RequestMapping("/user")
public class UserContoller {

	@Autowired
	Userrepo userrepo;
	
	@RequestMapping("/")
	public String userhome(Authentication authentication,HttpSession httpSession)
	{
		String email = authentication.getName();
		User user= this.userrepo.getUserByusername(email);
		//making the session for user after login
		httpSession.setAttribute("uid",user.getUid());
		httpSession.setAttribute("name",user.getName());
		httpSession.setAttribute("email",user.getEmail());
		return "user-home";
	}
	
	@RequestMapping("/post")
	public String postpage()
	{
		return "user-post";
	}
}
