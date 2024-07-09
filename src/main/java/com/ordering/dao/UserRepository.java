package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.Entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	public User findByEmail(String username);
}

