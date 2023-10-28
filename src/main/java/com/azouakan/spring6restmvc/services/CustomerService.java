package com.azouakan.spring6restmvc.services;

import com.azouakan.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> customersList();
    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomre(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    Boolean deleteCustomerById(UUID customerId);

    Optional<CustomerDTO> updateCustomerPatchById(UUID customerId, CustomerDTO customer);
}
