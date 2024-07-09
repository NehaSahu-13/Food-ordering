package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ordering.Entities.Member;
import com.ordering.Entities.User;

public interface MemberRepository extends JpaRepository<Member,Integer>{

	public Member findByEmail(String username);
	
}
