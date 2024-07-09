package com.ordering.Entities;

import java.util.Date;
import java.util.List;

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
public class Food {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	private String description;
	private long price;
	
	@ManyToOne
	private FoodCategory foodCategory;
	
	private String images;
	
	@ManyToOne
	private Restaurant restaurant;
	
	
	private String isVegetarian;
	private Date creationDate;
	
	@OneToMany(mappedBy="food")
	private List<OrderItem> orderItems;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public FoodCategory getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(FoodCategory foodCategory) {
		this.foodCategory = foodCategory;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getIsVegetarian() {
		return isVegetarian;
	}

	public void setIsVegetarian(String isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", foodCategory=" + foodCategory + ", images=" + images + ", restaurant=" + restaurant
				+ ", isVegetarian=" + isVegetarian + ", creationDate=" + creationDate + ", orderItems=" + orderItems
				+ "]";
	}

	public Food(int id, String name, String description, long price, FoodCategory foodCategory, String images,
			Restaurant restaurant, String isVegetarian, Date creationDate, List<OrderItem> orderItems) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.foodCategory = foodCategory;
		this.images = images;
		this.restaurant = restaurant;
		this.isVegetarian = isVegetarian;
		this.creationDate = creationDate;
		this.orderItems = orderItems;
	}

	public Food() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
	

	
	
}
