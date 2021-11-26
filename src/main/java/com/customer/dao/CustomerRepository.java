package com.customer.dao;

import com.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByOrderByIdAsc();
    @Query("SELECT c FROM Customer c WHERE c.firstName = ?1 or c.lastName = ?1")
    List<Customer> findByFirstNameOrLastName(String name);
}
