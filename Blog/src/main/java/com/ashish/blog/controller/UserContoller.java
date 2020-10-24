package com.ashish.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.Post;
import com.ashish.blog.entity.User;
import com.ashish.blog.helper.FileUploadHelper;
import com.ashish.blog.helper.Messages;

@Controller
@RequestMapping("/user")
public class UserContoller {

	@Autowired
	Userrepo userrepo;
	
	@Autowired
	Postrepo postrepo;
	
	@Autowired
	FileUploadHelper fileuploadhelper;
	
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
	public String postpage(Model model)
	{
		model.addAttribute("post", new Post());
		return "user-post";
	}
	
	@PostMapping("/addpost")
	public String addpost(@ModelAttribute("post") Post post,@RequestParam("postHeadImagefile") MultipartFile postHeadImagefile ,HttpSession httpSession,Model model,@RequestParam("submit-value") String submit)
	{
		try {
			String msg = null,alrttype=null;
			
			int id=(int)(httpSession.getAttribute("uid"));
			String name=(String)(httpSession.getAttribute("name"));
			if(name.equals("")||name.equals(null)||id==0)
			{
				throw new Exception("Please login and try again!!");
			}
			
			if(post.getPostHeading().equals("") || post.getPostHeading().equals(" "))
			{
				throw new Exception("Post Heading is must..");
			}
			if(post.getPostDescription().equals(""))
			{
				post.setPostDescription(null);
			}
			if(post.getPostContent().equals(""))
			{
				post.setPostContent(null);
			}
//			if(!(postHeadImagefile.getContentType().equals("image/png"))||!(postHeadImagefile.getContentType().equals("image/jpeg")))
//			{
//				throw new Exception("Image File type  must be ->png/jpg/jpeg !");
//			}
			
			if(postHeadImagefile.isEmpty())
			{
				post.setPostHeadImage(null);
			}
			else {
				boolean status=fileuploadhelper.uploadfile(postHeadImagefile);
				if(status)
				{
					System.out.println(ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(postHeadImagefile.getOriginalFilename()).toUriString());
					post.setPostHeadImage(ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(postHeadImagefile.getOriginalFilename()).toUriString());
				}
				else
				{
					post.setPostHeadImage(null);
				}
			}
			
			if(submit.equals("post"))
			{
				post.setPostStatus(true);
				msg="Your Post Has Been Posted  Successfully ✔";
				alrttype="alert-success";
			}
			if(submit.equals("draft"))
			{
				post.setPostStatus(false);
				msg="Your Post Has Been Saved to Draft Successfully 🏠";
				alrttype="alert-warning";
			}
			
			
//			System.out.println(postHeadImagefile.getOriginalFilename());
//			System.out.println(postHeadImagefile.getSize());
//			System.out.println(postHeadImagefile.getContentType());
			
			post.setId(id);
			post.setAuthorName(name);
			postrepo.save(post);
			model.addAttribute("post",  new Post());
			httpSession.setAttribute("message", new Messages(msg,alrttype));
			return "user-post";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("post",post);
			httpSession.setAttribute("message", new Messages("Something Went Wrong!! "+e.getMessage(),"alert-danger"));
			return "user-post";
		}
		
	}
	
	
	@RequestMapping("/me")
	public String aboutme()
	{
		return "user-info";
	}
	
	@RequestMapping("/posts")
	public String postdetail()
	{
		return "post";
	}
	
}
