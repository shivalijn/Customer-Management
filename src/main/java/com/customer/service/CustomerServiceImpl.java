package com.customer.service;

import com.customer.dao.CustomerRepository;
import com.customer.entity.Customer;
import com.customer.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getCustomersByName(String firstName) {
        return customerRepository.findByFirstNameOrLastName(firstName);
    }

    @Override
    public Customer updateCustomerAddress(Customer customer) throws CustomerNotFoundException {
        Customer existingCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for this id :: " + customer.getId()));

        existingCustomer.setStreetName(customer.getStreetName());
        existingCustomer.setHouseNr(customer.getHouseNr());
        existingCustomer.setHouseAddition(customer.getHouseAddition());
        existingCustomer.setPostalCode(customer.getPostalCode());
        existingCustomer.setCity(customer.getCity());

        return customerRepository.save(existingCustomer);
    }
}
