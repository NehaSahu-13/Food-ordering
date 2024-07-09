package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.Entities.Cart;


public interface CartRepository  extends JpaRepository<Cart,Integer>{

}
