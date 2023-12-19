package com.lozzby.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lozzby.Service.UserDetailsServiceCustom;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	
	@Autowired                     
	private UserDetailsServiceCustom userDetailsServiceCustom;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }

     
	 

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        AuthenticationManager manager = builder.build();

		return http
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/", "/shop/**", "/register", "/h2-console/**")
								.permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").permitAll()
						.failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("email")
						.passwordParameter("password"))
	                .oauth2Login(oauth2 -> oauth2
	                        .loginPage("/login")
	                        .successHandler(googleOAuth2SuccessHandler)
	                )
				.logout(logout -> logout.logoutUrl("/logout") 
						.logoutSuccessUrl("/login?logout=true")
						.invalidateHttpSession(true) // Invalidate session
						.deleteCookies("JSESSIONID")
				)

	//                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))

				.csrf(csrf -> csrf.disable())

				.headers(headers -> headers.frameOptions(option -> option.disable())).build();
	}

	
	
	
	 @Bean
	    public WebSecurityCustomizer webSecurityCustomizer(){
	        return (web) ->
	                web.ignoring()
	                        .requestMatchers("/resources/**", "/static/**","/images/**","/productImages/**","/css/**","/js/**");
	    }

}
