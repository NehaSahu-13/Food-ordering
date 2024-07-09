package com.ordering.Entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	private Cart cart;
	
	@ManyToOne
	private Food food;
	
	private int quantity;
	
	private long totalprice;

	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        CartItem cartItem = (CartItem) o;
	        return id == cartItem.id;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }  
	    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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

	public long getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(long totalprice) {
		this.totalprice = totalprice;
	}

	public CartItem(int id, Cart cart, Food food, int quantity, long totalprice) {
		super();
		this.id = id;
		this.cart = cart;
		this.food = food;
		this.quantity = quantity;
		this.totalprice = totalprice;
	}

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", cart=" + cart + ", food=" + food + ", quantity=" + quantity + ", totalprice="
				+ totalprice + "]";
	}
	
	
}
