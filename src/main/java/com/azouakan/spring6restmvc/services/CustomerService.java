package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> customersList();
    Optional<Customer> getCustomerById(UUID id);

    Customer saveNewCustomre(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);

    void updateCustomerPatchById(UUID customerId, Customer customer);
}
