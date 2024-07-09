package com.ordering.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@JsonIgnore
	@OneToOne
	private User user;
	
	private long total;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="cart",orphanRemoval=true,fetch = FetchType.EAGER)
	private List<CartItem>cartItems=new ArrayList<>();

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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Cart(int id, User user, long
			total, ArrayList<CartItem> cartItems) {
		super();
		this.id = id;
		this.user = user;
		this.total = total;
		this.cartItems = cartItems;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", user=" + user + ", total=" + total + ", cartItems=" + cartItems + "]";
	}
	
	
	
}
