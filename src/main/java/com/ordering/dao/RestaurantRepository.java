package com.ordering.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ordering.Entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer>{

	@Query("from Restaurant as c where c.owner.id=:ownerId")
	public Restaurant findRestaurantByOwner(@Param("ownerId") int ownerId);
	
	 @Query("SELECT r FROM Restaurant r WHERE r.address.city = :city")
	 List<Restaurant> findRestaurantsByCity(@Param("city") String city);
	 
	 public Restaurant findByName(@Param("name") String name);
	 

	    @Query("SELECT DISTINCT r FROM Restaurant r " +
	           "LEFT JOIN r.foods f " +
	           "LEFT JOIN r.foodCategory fc " +
	           "WHERE (f.name = :name OR fc.name = :name) " +
	           "AND r.address.city = :city")
	    List<Restaurant> findRestaurantsByNameAndCity(@Param("name") String name, @Param("city") String city);

}
