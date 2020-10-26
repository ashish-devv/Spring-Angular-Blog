package com.ashish.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ashish.blog.dao.Commentrepo;
import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.Comments;
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
	Commentrepo commentrepo;
	
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
					post.setPostHeadImage("/img/"+postHeadImagefile.getOriginalFilename());
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
	
	@RequestMapping("/post/{pid}")
	public String postdetail(@PathVariable int pid,Model model)
	{
		Post post= this.postrepo.findByPid(pid);
		List<Comments> comm=this.commentrepo.getAllCommentbyPostid(pid);
		if(post==null)
		{
			return "error";
		}
		if(post.isPostStatus()==false)
		{
			return "403";
		}
		model.addAttribute("post",post);
		model.addAttribute("comm",comm);
		model.addAttribute("comment", new Comments());
		return "post";
	}
	
	@PostMapping("/writecomment/{pid}")
	public String writecomment(@PathVariable int pid,@ModelAttribute("comment") Comments comment,HttpSession httpSession,Model model)
	{
		try
		{
			int id=(int)(httpSession.getAttribute("uid"));
			String name=(String)(httpSession.getAttribute("name"));
			if(name.equals("")||name.equals(null)||id==0)
			{
				throw new Exception("Please login and try again!!");
			}
			if(comment.getCommentcontent().equals("")||comment.getCommentcontent().equals(null))
			{
				throw new Exception("please Write the comment.");
			}
			comment.setUid(id);
			comment.setName(name);
			comment.setPid(pid);
			this.commentrepo.save(comment);
			httpSession.setAttribute("message", new Messages("Your Comment is Posted ✔!! ","alert-success"));
			return "redirect:/user/post/{pid}";
		}
		catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Something Went Wrong ❌!! ","alert-danger"));
			return "redirect:/user/post/{pid}";
		}
	}
	
	@RequestMapping("/user/{uid}")
	public String userprofile(@PathVariable("uid") int uid,Model model,HttpSession httpSession)
	{
		try {
			if(httpSession.getAttribute("uid")==null)
			{
				return "redirect:/user/";
			}
			int loggeduserid=(int) httpSession.getAttribute("uid");
			if(loggeduserid==uid)
			{
				return "redirect:/user/me";
			}
			User user=this.userrepo.getUserByUid(uid);
			if(user==null)
			{
				return "error";
			}
			int noofpost=this.postrepo.findNoofPostByid(uid);
			int noofcomment=this.commentrepo.noOfCommentbyUid(uid);
			List<Post> listofPost = this.postrepo.FindAllPostByUserId(uid);
			model.addAttribute("udetail", user);
			model.addAttribute("noofpost", noofpost);
			model.addAttribute("noofcomment", noofcomment);
			model.addAttribute("listofPost", listofPost);
			
			
			
			return "user-info";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
}
