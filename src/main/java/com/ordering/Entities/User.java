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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;
	private String phonenumber;
	private String image;
	private String userCity;
	
	
	
	@OneToOne(cascade=CascadeType.ALL,mappedBy="user",orphanRemoval=true)
	private Cart cart;


	@OneToMany(cascade=CascadeType.ALL,mappedBy="user",orphanRemoval=true)
	private List<OrderItem>orders=new ArrayList<>();
	

    @OneToMany(cascade=CascadeType.ALL,mappedBy="user",orphanRemoval=true)
    private List<Favourites>favourites=new ArrayList<>();

   
    @OneToMany(cascade=CascadeType.ALL,mappedBy="user",orphanRemoval=true,fetch = FetchType.EAGER)
	private List<Address>addresses=new ArrayList<>();

	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

    

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public List<Favourites> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Favourites> favourites) {
		this.favourites = favourites;
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

	

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<OrderItem> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderItem> orders) {
		this.orders = orders;
	}




	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	
    

	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phonenumber="
				+ phonenumber + ", image=" + image + ", userCity=" + userCity + ", cart=" + cart + ", orders=" + orders
				+ ", favourites=" + favourites + ", addresses=" + addresses + "]";
	}

	public User(int id, String name, String email, String password, String phonenumber, String image, String userCity,
			Cart cart, List<OrderItem> orders, List<Favourites> favourites, List<Address> addresses) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phonenumber = phonenumber;
		this.image = image;
		this.userCity = userCity;
		this.cart = cart;
		this.orders = orders;
		this.favourites = favourites;
		this.addresses = addresses;
	}

	
	
	
	
	}
