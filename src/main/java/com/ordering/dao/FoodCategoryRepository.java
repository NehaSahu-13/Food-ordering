package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.Entities.FoodCategory;

public interface FoodCategoryRepository  extends JpaRepository<FoodCategory,Integer>{

	public FoodCategory findByName(String name);
}
