package com.azouakan.spring6restmvc.controller;

import com.azouakan.spring6restmvc.entities.Customer;
import com.azouakan.spring6restmvc.mappers.CustomerMapper;
import com.azouakan.spring6restmvc.model.CustomerDTO;
import com.azouakan.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerControllerIT {

    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testPatchByIdNotFound() {
        assertThrows(NotFoundException.class, ()->customerController.updateCustomerPatchById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void testPatchByIdFound() {
        Customer customer = customerRepository.findAll().get(0);
        final String customerName = "Updated Customer";
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerName(customerName);
        ResponseEntity<CustomerDTO> responseEntity = customerController.updateCustomerPatchById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(savedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, ()-> customerController.deleteById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByIdFound() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity<CustomerDTO> responseEntity = customerController.deleteById(customer.getId());
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, ()-> customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        final String customerName = "Updated Customer";
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerName(customerName);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        ResponseEntity<CustomerDTO> responseEntity = customerController.updateById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(savedCustomer.getCustomerName()).isEqualTo(customerName);



    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {
        CustomerDTO dto = CustomerDTO.builder()
                .customerName("New Customer")
                .build();
        ResponseEntity<CustomerDTO> responseEntity = customerController.handlePost(dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, ()->customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.CustomerList();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testCustomersList() {
        List<CustomerDTO> dtos = customerController.CustomerList();
        assertThat(dtos.size()).isEqualTo(3);
    }
}
