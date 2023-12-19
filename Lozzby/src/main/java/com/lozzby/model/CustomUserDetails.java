package com.lozzby.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.NoArgsConstructor;



@NoArgsConstructor
public class CustomUserDetails extends User implements UserDetails {

	

	public CustomUserDetails(User user) {
		super(user);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritiyList = new ArrayList<>();
		super.getRoles().forEach(role -> {
			authoritiyList.add(new SimpleGrantedAuthority(role.getName()));
		});
		return authoritiyList;
	}

	@Override
	public String getUsername() {
		
		return super.getEmail();
	}
	@Override
	public String getPassword() {
		
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
