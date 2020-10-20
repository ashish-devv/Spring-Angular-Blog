package com.ashish.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserContoller {

	@RequestMapping("/")
	@ResponseBody
	public String userhome(Authentication authentication,HttpSession httpSession)
	{
		String user = authentication.getName();
		//making the session for user after login
		httpSession.setAttribute("userid",user);
		return "Welcome !"+user+" <br><a href='/logout'>logout</a>";
	}
}
