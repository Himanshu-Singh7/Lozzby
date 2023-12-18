package com.lozzby.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/", "/shop/**", "/register", "/h2-console/**")
								.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").permitAll()
						.failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("email")
						.passwordParameter("password"))
//	                .oauth2Login(oauth2 -> oauth2
//	                        .loginPage("/login")
//	                        .successHandler("googleOAuth2SuccessHandler")
//	                )
				.logout(logout -> logout.logoutUrl("/logout") // URL to trigger logout
						.logoutSuccessUrl("/login?logout=true") // Redirect after logout
						.invalidateHttpSession(true) // Invalidate session
						.deleteCookies("JSESSIONID") // Delete cookies
				)

//	                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))

				.csrf(csrf -> csrf.disable())

				.headers(headers -> headers.frameOptions(option -> option.disable())).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
     
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
	
//	@Bean
//    public UserDetailsService userDetailsService(){
//		
//     
//
//      return new InMemoryUserDetailsManager(userDetails1,userDetails2);
//    }

}
