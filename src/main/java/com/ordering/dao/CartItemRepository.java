package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.Entities.CartItem;

public interface CartItemRepository  extends JpaRepository<CartItem,Integer>{

}
