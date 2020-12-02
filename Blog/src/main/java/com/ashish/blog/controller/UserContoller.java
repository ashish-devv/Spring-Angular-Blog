package com.ashish.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ashish.blog.dao.Commentrepo;
import com.ashish.blog.dao.Likerepo;
import com.ashish.blog.dao.Postrepo;
import com.ashish.blog.dao.Tagrepo;
import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.Comments;
import com.ashish.blog.entity.Post;
import com.ashish.blog.entity.Tag;
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
	Tagrepo tagrepo;
	
	@Autowired
	Likerepo likerepo;
	
	@Autowired
	FileUploadHelper fileuploadhelper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@RequestMapping("/")
	public String userhome(Authentication authentication,HttpSession httpSession)
	{
		String email = authentication.getName();
		User user= this.userrepo.getUserByusername(email);
		//making the session for user after login
		httpSession.setAttribute("uid",user.getUid());
		httpSession.setAttribute("name",user.getName());
		httpSession.setAttribute("email",user.getEmail());
		httpSession.setAttribute("propic",user.getProfilepic());
		httpSession.setAttribute("role", user.getRole());
		return "user-home";
	}
	
	@RequestMapping("/post")
	public String postpage(Model model)
	{
		List<Tag> t=this.tagrepo.FindAllTag();
		model.addAttribute("tags", t);
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
				LocalDateTime now = LocalDateTime.now();
				String Filename=now+StringUtils.cleanPath(postHeadImagefile.getOriginalFilename());
				Filename=Filename.toLowerCase().replaceAll(":", "-");
				boolean status=fileuploadhelper.uploadfile(postHeadImagefile,Filename);
				if(status)
				{

					post.setPostHeadImage("/img/"+Filename);
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
			
			List<Tag> t=this.tagrepo.FindAllTag();
			model.addAttribute("tags", t);
			
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
	public String aboutme(HttpSession httpSession,Model model)
	{
		try {
			if(httpSession.getAttribute("uid")!=null)
			{
				int id=(int) httpSession.getAttribute("uid");
				User user=this.userrepo.getUserByUid(id);
				if(user==null)
				{
					return "redirect:/user/";
				}
				int noofpost=this.postrepo.findNoofPostByid(id);
				int noofcomment=this.commentrepo.noOfCommentbyUid(id);
				List<Post> listofPost = this.postrepo.FindAllPostByUserId(id);
				model.addAttribute("udetail", user);
				model.addAttribute("noofpost", noofpost);
				model.addAttribute("noofcomment", noofcomment);
				model.addAttribute("listofPost", listofPost);
				model.addAttribute("follow", false);
				model.addAttribute("editable", true);
			}
			else {
				return "redirect:/user/";
			}
			return "user-info";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/user/";
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
			model.addAttribute("follow", true);
			
			
			
			return "user-info";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
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
	
	@PostMapping("/addtag")
	public String addtag(@ModelAttribute Tag tag,HttpSession httpSession)
	{
		try {
			if(httpSession.getAttribute("uid") != null)
			{
				int id=(int) httpSession.getAttribute("uid");
				tag.setTagbyuid(id);
				Tag tagg=this.tagrepo.save(tag);
				httpSession.setAttribute("message", new Messages("Tag Added Successfully ✔","alert-success"));
				//System.out.println(tagg);
				
			}
			else
			{
				throw new Exception("First Login ...");
			}
			return "redirect:/user/";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Use Unique Tag Name, This tag Name is Already Created ❌:Something Went Wrong!❌","alert-danger"));
			return "redirect:/user/";
			// TODO: handle exception
		}
		
	}
	
	@GetMapping("/settings")
	public String settings(Model model,HttpSession httpSession)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			User user=this.userrepo.getUserByUid(uid);
			model.addAttribute("profile", user);
			return "accountsetting";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@PostMapping("/changepassword")
	public String changepassword(@RequestParam("npwd") String newpassword,Authentication authentication,HttpSession httpSession)
	{
		try {
			String email = authentication.getName();
			User user= this.userrepo.getUserByusername(email);
			user.setPassword(passwordEncoder.encode(newpassword ));
			this.userrepo.save(user);
			httpSession.setAttribute("message", new Messages("Password changed SuccessFully!","alert-success"));

			return"redirect:/user/settings";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Something Is Wrong! Please try Again After Login!","alert-danger"));
			return"redirect:/user/settings";
		}
	}
	
	@PostMapping("/addprofilepic")
	public String adddp(HttpSession httpSession,@RequestParam("propic") MultipartFile propic)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			User u=this.userrepo.getUserByUid(uid);
			if(propic.isEmpty())
			{
				throw new Exception("Profile Picture is Empty !");
			}
			else {
				LocalDateTime now = LocalDateTime.now();
				String Filename=now+StringUtils.cleanPath(propic.getOriginalFilename());
				Filename=Filename.toLowerCase().replaceAll(":", "-");
				boolean status=fileuploadhelper.uploadfile(propic,Filename);
				if(status)
				{

					u.setProfilepic("/img/"+Filename);
					this.userrepo.save(u);
					httpSession.setAttribute("message", new Messages("Profile Picture Posted !","alert-success"));
				}
				
			}
			return "redirect:/user/settings";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Something Went Wrong!","alert-danger"));
			return "redirect:/user/settings";
			
		}
	}
	
	@PostMapping("/editaccountdetail")
	public String editaccountdetail(HttpSession httpSession,@ModelAttribute User u)
	{
		try {
			int uid=(int)httpSession.getAttribute("uid");
			User user=this.userrepo.getUserByUid(uid);
			if(user!=null)
			{
				System.out.println(u);
				user.setAboutme(u.getAboutme());
				user.setFbid(u.getFbid());
				user.setGithubid(u.getGithubid());
				user.setInstaid(u.getInstaid());
				user.setTwitterid(u.getTwitterid());
				System.out.println(user);
				
				this.userrepo.save(user);
				httpSession.setAttribute("message", new Messages("Saved Changes Successfully!🎯","alert-success"));
			}
			return "redirect:/user/settings";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Something Went Wrong!","alert-danger"));
			return "redirect:/user/settings";
			
		}
	}
	
	@GetMapping("/deleteaccount")
	public String deleteaccount(HttpSession httpSession)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			
			this.userrepo.deleteById(uid);
			
			return "redirect:/logout";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Something Went Wrong!"+e.getMessage(),"alert-danger"));
			return "redirect:/user/settings";
		}
	}
	
	@GetMapping("/deletepost/{pid}")
	public String deletepost(@PathVariable("pid") int pid,HttpSession httpSession)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			Post post =this.postrepo.findByPid(pid);
			if(post.getId()==uid)
			{
				this.postrepo.deleteById(pid);
				httpSession.setAttribute("message", new Messages("Post Deleted !🎯","alert-success"));
			}
			else
			{
				httpSession.setAttribute("message", new Messages("Some Error Occured!","alert-danger"));
			}
			return"redirect:/user/me";
		} catch (Exception e) {
			e.printStackTrace();
			return"redirect:/user/";
		}
	}
	
	
	@GetMapping("/savedtopost/{pid}")
	public String savetopost(@PathVariable("pid") int pid,HttpSession httpSession)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			Post post =this.postrepo.findByPid(pid);
			if(post.getId()==uid)
			{
				post.setPostStatus(true);
				this.postrepo.save(post);
				httpSession.setAttribute("message", new Messages("Draft is Posted!🎯","alert-success"));
			}
			return "redirect:/user/me";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", new Messages("Something Is Wrong !🎯","alert-danger"));
			return"redirect:/user/me";
		}
	}
	
	
	@GetMapping("/editpost/{pid}")
	public String editpost(@PathVariable("pid") int pid,HttpSession httpSession,Model model)
	{
		try {
			int uid=(int) httpSession.getAttribute("uid");
			Post post =this.postrepo.findByPid(pid);
			if(post.getId()==uid)
			{
				List<Tag> t=this.tagrepo.FindAllTag();
				model.addAttribute("tags", t);
				model.addAttribute("post", post);
			}
			return "editpost";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/user/";
		}
	}
	
	@GetMapping("/tag")
	public String showtag(Model model)
	{
		try 
		{
			List<Tag> tags= this.tagrepo.FindAllTag();
			model.addAttribute("taglist", tags);
			return "tag";
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
	
	@GetMapping("/tag/{tagid}")
	public String viewtag(@PathVariable("tagid") int tagid,Model model)
	{
		try {
			Tag tag = this.tagrepo.findByTagid(tagid);
			if(tag==null)
			{
				throw new Exception("No tag found by this id"); 
			}
			else
			{
				int countofpostbythistag=this.postrepo.findnoofpostbytag(tag.getTag_name());
				model.addAttribute("taginfo",tag);
				model.addAttribute("countofpost", countofpostbythistag);
				System.out.println(tag);
			}
			return "tagview";
		} catch (Exception e) {
			e.printStackTrace();
			return "404";
		}
	}
	
	@GetMapping("/follower")
	public String followerpage(Model model)
	{
		try 
		{
			return "follower";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "404";
		}
	}
	
	@GetMapping("/readinglist")
	public String readinglist()
	{
		try {
			return "readinglist";
		} catch (Exception e) {
			e.printStackTrace();
			return "404";
		}
	}
	
	
}
