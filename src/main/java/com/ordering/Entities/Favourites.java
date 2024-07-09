package com.ordering.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Favourites {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	
	private String image;
	
	private String description;
	
	@JsonIgnore
	@ManyToOne
	private User user;
	
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	


	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Favourites() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Favourites(int id, String title, String image, String description, User user) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.description = description;
		this.user = user;
	}



	@Override
	public String toString() {
		return "Favourites [id=" + id + ", title=" + title + ", image=" + image + ", description=" + description
				+ ", user=" + user + "]";
	}

	
	
}
