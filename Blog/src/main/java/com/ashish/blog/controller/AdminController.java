package com.ashish.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.User;
import com.ashish.blog.helper.Messages;




@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Userrepo us;
	
	
	@GetMapping("/")
	public String adminindex(Model model)
	{
		model.addAttribute("user", new User());
		return "admin/adminindex";
	}
	
	@PostMapping("/addadmin")
	public String addadmin(Model model,@ModelAttribute("user") User user,HttpSession session)
	{
		try {
			user.setRole("ROLE_ADMIN");
			user.setEnabled(true);
			user.setProfilepic("/img/default.jpg");
			user.setAboutme("404 Bio Not Found ❌");
			String email=user.getRegistration();
			email=email+"@cutm.ac.in";
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(user.getPassword() ));
			//System.out.println("user"+ user);
			this.us.save(user);
			model.addAttribute("user",  new User());
			session.setAttribute("message", new Messages("You have Registered Successfully!!","alert-success"));
			return "admin/adminindex";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Messages("Use Unique Id For CUTM Domain.Retry!!!","alert-danger"));
			return "admin/adminindex";
		}
	}

}
