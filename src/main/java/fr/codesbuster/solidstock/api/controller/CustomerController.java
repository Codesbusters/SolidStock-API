package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.payload.CustomerDto;
import fr.codesbuster.solidstock.api.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerDto customerDto) {

        if (customerDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getName() == null || customerDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getAddress() == null || customerDto.getAddress().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getCorporation() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getCorporateName() == null || customerDto.getCorporateName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getSiret() == null || customerDto.getSiret().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getSiren() == null || customerDto.getSiren().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getRib() == null || customerDto.getRib().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getRcsInteger() == null || customerDto.getRcsInteger().equals(0)) {
            return ResponseEntity.badRequest().build();
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerDto.getName());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setCorporation(customerDto.getCorporation());
        customerEntity.setCorporateName(customerDto.getCorporateName());
        customerEntity.setSiren(customerDto.getSiren());
        customerEntity.setSiret(customerDto.getSiret());
        customerEntity.setRib(customerDto.getRib());
        customerEntity.setRcsInteger(customerDto.getRcsInteger());

        log.info("CustomerEntity: " + customerEntity);

        customerEntity = customerService.createCustomer(customerEntity);

        return ResponseEntity.ok(customerEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<CustomerEntity>> getAllCustomer() {
        Iterable<CustomerEntity> customerEntities = customerService.getCustomers();
        return ResponseEntity.ok(customerEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long id) {
        CustomerEntity customerEntity = customerService.getCustomer(id);
        return ResponseEntity.ok(customerEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        CustomerEntity customerEntity = customerService.getCustomer(id);
        customerEntity.setName(customerDto.getName());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setCorporation(customerDto.getCorporation());
        customerEntity.setCorporateName(customerDto.getCorporateName());
        customerEntity.setSiren(customerDto.getSiren());
        customerEntity.setSiret(customerDto.getSiret());
        customerEntity.setRib(customerDto.getRib());
        customerEntity.setRcsInteger(customerDto.getRcsInteger());
        customerEntity = customerService.updateCustomer(customerEntity);
        return ResponseEntity.ok(customerEntity);
    }

}