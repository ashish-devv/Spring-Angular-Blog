package com.ashish.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/")
	public String adminindex(Model model)
	{
		return "admin/adminindex";
	}
	
	@PostMapping("/addadmin")
	public String addadmin(Model model)
	{
		try {
			return "admin/adminindex";
		} catch (Exception e) {
			e.printStackTrace();
			return "admin/adminindex";
		}
	}

}
