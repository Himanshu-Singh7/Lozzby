package com.lozzby.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lozzby.model.CustomUserDetails;
import com.lozzby.model.User;
import com.lozzby.repository.UserRepo;

@Service
public class UserDetailsServiceCustom implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = this.userRepo.findUserByEmail(username);
		user.orElseThrow(() -> new UsernameNotFoundException("User is Not Found"));
		return user.map(CustomUserDetails :: new ).get();
	}

}
