package com.customer.controller;

import com.customer.entity.Customer;
import com.customer.exception.PostalCodeNotValidException;
import com.customer.exception.CustomerNotFoundException;
import com.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    private ResponseEntity<String> addNewCustomer(@RequestBody Customer customer) throws PostalCodeNotValidException {
        try {
            customerService.createCustomer(customer);
        } catch (ConstraintViolationException e) {
            throw new PostalCodeNotValidException("Invalid Postal Code");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for this id :: " + id));
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping(value = "/customers/name/{name}")
    public ResponseEntity<List<Customer>> getCustomersByName(@PathVariable String name) {
        List<Customer> customerResult = customerService.getCustomersByName(name);
        if (!customerResult.isEmpty())
            return new ResponseEntity<>(customerResult, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomerAddress(@RequestBody Customer customer) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.updateCustomerAddress(customer), HttpStatus.OK);
    }
}
