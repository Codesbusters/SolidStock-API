package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    CustomerEntity createCustomer(CustomerEntity customerEntity);

    List<CustomerEntity> getCustomers();

    void deleteCustomer(Long id);

    CustomerEntity updateCustomer(CustomerEntity customerEntity);

    CustomerEntity getCustomer(Long id);
}