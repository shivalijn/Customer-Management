package com.customer.service;

import com.customer.entity.Customer;
import com.customer.exception.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(int id);
    List<Customer> getCustomersByName(String firstName);
    Customer updateCustomerAddress(Customer customer) throws CustomerNotFoundException;
}
