package com.azouakan.spring6restmvc.controller;

import com.azouakan.spring6restmvc.entities.Customer;
import com.azouakan.spring6restmvc.model.CustomerDTO;
import com.azouakan.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
