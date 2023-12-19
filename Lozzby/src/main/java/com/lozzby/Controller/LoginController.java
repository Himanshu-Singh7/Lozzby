package com.lozzby.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.lozzby.model.Role;
import com.lozzby.model.User;
import com.lozzby.repository.RoleRepo;
import com.lozzby.repository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class LoginController {
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@GetMapping("/login")
	public String login() {
		return "login";
		
	}
	
	@GetMapping("/register")
	public String registerGet() {
	 
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user")User user, HttpServletRequest request ) throws ServletException {
		String password = user.getPassword();
		user.setPassword(this.passwordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findById((long) 2).get());
		user.setRoles(roles);
		this.userRepo.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
		
	}
	
}
