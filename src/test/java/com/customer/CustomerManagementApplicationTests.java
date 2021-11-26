package com.customer;

import com.customer.dao.CustomerRepository;
import com.customer.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerManagementApplicationTests {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    void testAddNewCustomer() {
        ResponseEntity<Customer> postResponse = restTemplate.postForEntity(getRootUrl(), getCustomerObject(), Customer.class);
        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
    }

    @Test
    @Order(2)
    public void testGetAllCustomers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl(),
                HttpMethod.GET, entity, String.class);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetCustomerById() {
        // setup
        when(customerRepository.findById(1)).thenReturn(Optional.of(getCustomerObject()));

        // execute
        ResponseEntity<Customer> response = restTemplate.getForEntity(getRootUrl() + "/customers/1", Customer.class);

        // verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(getCustomerObject(), response.getBody());
    }

    @Test
    @Order(4)
    public void testGetCustomerById_CustomerNotFound() {
        // setup
        when(customerRepository.findById(4)).thenReturn(Optional.empty());

        // execute
        ResponseEntity<Customer> response = restTemplate.getForEntity(getRootUrl() + "/customers/1", Customer.class);

        // verify
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testGetCustomersByName() {
        // setup
        when(customerRepository.findByFirstNameOrLastName("Jon")).thenReturn(List.of(getCustomerObject()));

        // execute
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(getRootUrl() + "/customers/name/Jon", Customer[].class);

        // verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @Order(6)
    public void testUpdateCustomerAddress() {
        int id = 1;
        Customer customer = restTemplate.getForObject(getRootUrl() + "/customers/" + 1, Customer.class);
        customer.setHouseNr(20);
        restTemplate.put(getRootUrl() + "/employees/" + id, customer);
        Customer updatedEmployee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Customer.class);
        Assertions.assertNotNull(updatedEmployee);
    }

    private Customer getCustomerObject() {
        return Customer.builder()
                .id(1)
                .firstName("Jon")
                .lastName("Snow")
                .streetName("Market")
                .houseNr(7)
                .postalCode("1155AB")
                .city("Amsterdam")
                .dateOfBirth(LocalDate.of(2010,11,25))
                .build();
    }

}
