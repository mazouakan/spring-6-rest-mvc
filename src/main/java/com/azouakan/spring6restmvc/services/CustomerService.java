package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> customersList();
    Customer getCustomerById(UUID id);

    Customer saveNewCustomre(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);
}
