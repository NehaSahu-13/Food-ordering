package com.ordering.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Address {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String StreetAddress;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String country;
	
	
	@ManyToOne
	private User user;
	
	@OneToOne
	private Restaurant restaurant;
	
	@OneToMany(mappedBy="deliveryAddress")
	private List<OrderItem> orderItems;
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreetAddress() {
		return StreetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		StreetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", StreetAddress=" + StreetAddress + ", city=" + city + ", stateProvince="
				+ stateProvince + ", postalCode=" + postalCode + ", country=" + country + ", user=" + user
				+ ", restaurant=" + restaurant + ", orderItems=" + orderItems + "]";
	}
	public Address(int id, String streetAddress, String city, String stateProvince, String postalCode, String country,
			User user, Restaurant restaurant, List<OrderItem> orderItems) {
		super();
		this.id = id;
		StreetAddress = streetAddress;
		this.city = city;
		this.stateProvince = stateProvince;
		this.postalCode = postalCode;
		this.country = country;
		this.user = user;
		this.restaurant = restaurant;
		this.orderItems = orderItems;
	}
	
	
	
	
	
	
	
	
	
	
}
