package com.ordering.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ordering.Entities.Food;


public interface FoodRepository  extends JpaRepository<Food,Integer>{
	 
	 @Query("SELECT f FROM Food f WHERE f.restaurant.address.city = :city")
	    List<Food> findFoodsByCity(@Param("city") String city);
	 
	 @Query("SELECT f FROM Food f WHERE f.restaurant.address.city = :city AND f.foodCategory.name = :foodCategoryName")
	 List<Food> findFoodsByCityAndFoodName(@Param("city") String city, @Param("foodCategoryName") String foodCategoryName);

//	 @Query("SELECT f FROM Food f WHERE (f.name = :name OR foodCategory.name = :foodCategoryName) AND f.restaurant.address.city = :city")
//	 List<Food> findFoodsByNameOrCategoryAndCity(@Param("name") String name, @Param("foodCategoryName") String category, @Param("city") String city);

	 
}
