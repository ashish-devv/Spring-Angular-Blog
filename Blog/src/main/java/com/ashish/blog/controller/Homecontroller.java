package com.ashish.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.User;
import com.ashish.blog.helper.Messages;

@Controller
public class Homecontroller {
	
	@Autowired
	private Userrepo us;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String home()
	{
		return "home-blog";
	}
	
	@GetMapping("/signin")
	public String customlogin()
	{
		return "signin";
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/register")
	public String registeruser(@ModelAttribute("user") User user,@RequestParam(value="agree",defaultValue = "false") boolean agree,Model model,HttpSession session)
	{
		try {
			
			if(!agree)
			{
				//System.out.println("Please Agree to Terms and Condition!");
				throw new Exception("Please Agree to Terms and Condition!!");
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setProfilepic("/img/default.png");
			user.setAboutme("404 Bio Not Found ❌");
			String email=user.getRegistration();
			email=email+"@cutm.ac.in";
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(user.getPassword() ));
			//System.out.println("agree"+agree);
			//System.out.println("user"+ user);
			this.us.save(user);
			model.addAttribute("user",  new User());
			session.setAttribute("message", new Messages("You have Registered Successfully!!","alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Messages("Something Went Wrong!!","alert-danger"));
			return "signup";
		}
		
	}

}
