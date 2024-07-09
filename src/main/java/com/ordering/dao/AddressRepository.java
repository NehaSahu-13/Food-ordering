package com.ordering.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.Entities.Address;

public interface AddressRepository  extends JpaRepository<Address,Integer>{

}
