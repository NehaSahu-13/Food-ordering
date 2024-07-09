package com.ordering.Entities;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	private User user;
	
	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;
	
	@ManyToOne
	private Food food;
	
	private int quantity;
	
	private long totalPrice;

    private String orderStatus;
	
	private Date createdAt;
	
	@ManyToOne
	private Address deliveryAddress;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", user=" + user + ", restaurant=" + restaurant + ", food=" + food
				+ ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", orderStatus=" + orderStatus
				+ ", createdAt=" + createdAt + ", deliveryAddress=" + deliveryAddress + "]";
	}

	public OrderItem(int id, User user, Restaurant restaurant, Food food, int quantity, long totalPrice,
			String orderStatus, Date createdAt, Address deliveryAddress) {
		super();
		this.id = id;
		this.user = user;
		this.restaurant = restaurant;
		this.food = food;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.createdAt = createdAt;
		this.deliveryAddress = deliveryAddress;
	}

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
