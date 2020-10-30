package com.ashish.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ashish.blog.entity.Forgetpass;

@Controller
public class ForgetPassword {
	
	@GetMapping("/forgetpassword")
	public String forgetpassword(Model model)
	{
		model.addAttribute("forgetpass", new Forgetpass());
		return "forgetpass";
	}
	
	@PostMapping("/sendlink")
	public String sendlink(@ModelAttribute("forgetpass") Forgetpass forgetpass)
	{
		try {
			System.out.println(forgetpass);
			return "forgetpass";
		} catch (Exception e) {
			e.printStackTrace();
			return "forgetpass";
		}
		
	}

}
