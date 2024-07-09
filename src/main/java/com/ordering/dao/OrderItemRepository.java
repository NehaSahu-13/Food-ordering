package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.Entities.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Integer>{

}
