package com.ordering.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class MyConfig {
//	
//	@Autowired
//	private JwtAuthenticationEntryPoint
//	
//
	@Bean
	public UserDetailsService getUserDetailService() {
		
		return new MemberDetailsServiceimpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	  public DaoAuthenticationProvider authenticationProvider()
	  {
		  DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		  daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		  daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		  
		  return daoAuthenticationProvider;
	  }
	
	
	//configure method
	//.formLogin()- form based login
	 
	 @Bean
	  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
	  {
		
		  
		  
		  
		  httpSecurity.authorizeRequests()
		  .requestMatchers("/member/**")
		  .hasAnyRole("RESTAURANT-OWNER", "ADMIN")
		  .requestMatchers("/**")
		  .permitAll()
		  .anyRequest()
		  .authenticated()
		  .and()
		  .formLogin()
		  .loginPage("/login-member")
		  .loginProcessingUrl("/domemberlogin")
		  .defaultSuccessUrl("/member/dashboard")
		  .and()
		  .csrf().disable();
		  
		  return httpSecurity.build();
		  
   }
	 
	

	    
	 
}
