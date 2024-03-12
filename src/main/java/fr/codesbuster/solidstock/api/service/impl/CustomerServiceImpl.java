package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer cannot be null");
        }

        if (customerRepository.existsByName(customerEntity.getName())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer already exists");
        }

        return customerRepository.save(customerEntity);
    }

    @Override
    public List<CustomerEntity> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer id cannot be null");
        }

        if (!customerRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer does not exist");
        }

        customerRepository.deleteById(id);
    }

    @Override
    public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer cannot be null");
        }

        if (!customerRepository.existsById(customerEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer does not exist");
        }

        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity getCustomer(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer id cannot be null");
        }

        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);

        if (customerEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer does not exist");
        }

        return customerEntity.get();
    }
}