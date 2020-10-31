package com.ashish.blog.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashish.blog.config.EmailConfig;
import com.ashish.blog.dao.ForgetPassrepo;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.Forgetpass;
import com.ashish.blog.entity.User;
import com.ashish.blog.helper.Messages;

@Controller
public class ForgetPassword {
	
	@Autowired
	EmailConfig emailconfig;
	
	@Autowired
	Userrepo userrepo;
	
	@Autowired
	ForgetPassrepo forgetpassrepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/forgetpassword")
	public String forgetpassword(Model model)
	{
		model.addAttribute("forgetpass", new Forgetpass());
		return "forgetpass";
	}
	
	@PostMapping("/sendlink")
	public String sendlink(@ModelAttribute("forgetpass") Forgetpass forgetpass,HttpSession httpSession,HttpServletRequest request)
	{
		try {
			//System.out.println(forgetpass);
			User user=this.userrepo.getUserByusername(forgetpass.getEmail());
			if(user==null)
			{
				throw new Exception("User is not registered on the site Yet! ❌");
			}
			else
			{
				Random rnd = new Random();
			    int number = rnd.nextInt(999999);
				 
				forgetpass.setUniquekey(number);
			    
				JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
				mailSenderImpl.setHost(this.emailconfig.getHost());
				mailSenderImpl.setPort(this.emailconfig.getPort());
				mailSenderImpl.setUsername(this.emailconfig.getUsername());
				mailSenderImpl.setPassword(this.emailconfig.getPassword());
				
				
				String message=request.getScheme()+"://"+request.getServerName()+"/changepassword/"+ number;
				
				SimpleMailMessage mailMessage=new SimpleMailMessage();
				mailMessage.setFrom("d2a551ffc5-f09088@inbox.mailtrap.io");
				mailMessage.setTo(forgetpass.getEmail());
				mailMessage.setSubject("Password Change Link From BLOG");
				mailMessage.setText(message);
				
				mailSenderImpl.send(mailMessage);
				
				httpSession.setAttribute("message", new Messages("Link Sent SuccessFully 📧 ✅","alert-success"));
				this.forgetpassrepo.save(forgetpass);
			}
			return "forgetpass";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages(e.getMessage(),"alert-danger"));
			return "forgetpass";
		}
		
	}
	
	
	
	@GetMapping("/changepassword/{id}")
	public String changepassword(@PathVariable("id") int uniqueid,Model model)
	{
		try {
			Forgetpass forgetpass=this.forgetpassrepo.checkuser(uniqueid);
			if(forgetpass!=null)
			{
				model.addAttribute("forgetpass", forgetpass);
				return "changepassword";
			}
			else
			{
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@PostMapping("/changepassword/{id}")
	public String changepasswordd(@PathVariable("id") int uniqueid,@RequestParam("pwd") String password,Model model)
	{
		try {
			Forgetpass forgetpass=this.forgetpassrepo.checkuser(uniqueid);
			if(forgetpass!=null)
			{
				User user = this.userrepo.getUserByusername(forgetpass.getEmail());
				user.setPassword(passwordEncoder.encode(password));
				this.userrepo.save(user);
				this.forgetpassrepo.deleteById(forgetpass.getId());
				return "redirect:/user/";
			}
			else
			{
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}
