package com.ordering.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JsonIgnore
	private Member owner;
	
	private String name;
	private String description;

	
	@OneToOne(mappedBy="restaurant",cascade=CascadeType.ALL,orphanRemoval=true)
	private Address address;
	private String email;
	private String mobile;
	
	private String openingHours;
	
	@OneToMany(mappedBy="restaurant",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<OrderItem>orders=new ArrayList<>();
	
	private String image;
	
	private Date registrationDate;
	
	@JsonIgnore
	@OneToMany(mappedBy="restaurant",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Food>foods=new ArrayList<>();

	@OneToMany(mappedBy="restaurant",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<FoodCategory>foodCategory=new ArrayList<>();
	
	

public Restaurant(int id, Member owner, String name, String description, Address address,
			String email, String mobile, String openingHours, List<OrderItem> orders, String image,
			Date registrationDate, List<Food> foods, List<FoodCategory> foodCategory) {
		super();
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
		this.openingHours = openingHours;
		this.orders = orders;
		this.image = image;
		this.registrationDate = registrationDate;
		this.foods = foods;
		this.foodCategory = foodCategory;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public List<OrderItem> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderItem> orders) {
		this.orders = orders;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationData(Date Date) {
		this.registrationDate = Date;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}


  
	public List<FoodCategory> getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(List<FoodCategory> foodCategory) {
		this.foodCategory = foodCategory;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	
   
	
	
}
