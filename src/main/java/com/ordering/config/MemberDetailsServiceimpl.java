package com.ordering.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ordering.Entities.Member;
import com.ordering.dao.MemberRepository;


public class MemberDetailsServiceimpl implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member=memberRepository.findByEmail(username);
		
		if(member==null) {
			throw new UsernameNotFoundException("could not found user !!");
		}
		
		CustomMemberDetails customUserDetails=new CustomMemberDetails(member);
		
		return customUserDetails;
	}

}
