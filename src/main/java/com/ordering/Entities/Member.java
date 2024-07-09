package com.ordering.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Member {
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;
	private String role;
	private String phonenumber;
	@OneToOne(cascade=CascadeType.ALL,mappedBy="owner",orphanRemoval=true)
	private Restaurant restaurant;
	private String image;
	
	
	
	
	public Member(int id, String name, String email, String password, String role, String phonenumber,
			Restaurant restaurant, String image) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phonenumber = phonenumber;
		this.restaurant = restaurant;
		this.image = image;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", phonenumber=" + phonenumber + ", restaurant=" + restaurant + ", image=" + image + "]";
	}


	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
