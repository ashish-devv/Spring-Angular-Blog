package com.ashish.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ashish.blog.dao.Userrepo;
import com.ashish.blog.entity.User;

public class UserdetailServiceimpl implements UserDetailsService {
	
	@Autowired
	private Userrepo userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userrepo.getUserByusername(username);
		if (user==null)
		{
			throw new UsernameNotFoundException("Could not Found User!!");
		}
		
		CustomUserDetail customUserDetail=new CustomUserDetail(user);
		
		
		return customUserDetail;
	}

}
