package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void createCustomer_ValidCustomer_ReturnsSavedCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setName("TestCustomer");

        try {
            CustomerEntity savedCustomer = customerService.createCustomer(customer);

            assertNotNull(savedCustomer);
            assertEquals("TestCustomer", savedCustomer.getName());
        } finally {
            // Remove the created customer
            customerRepository.deleteAll();
        }
    }

    @Test
    void createCustomer_NullCustomer_ThrowsAPIException() {
        assertThrows(APIException.class, () -> customerService.createCustomer(null));
    }

    @Test
    void getCustomers_ReturnsListOfCustomers() {
        // Crée une client pour le test
        CustomerEntity customer = new CustomerEntity();
        customer.setName("TestCustomer");
        customer.setAddress("TestCustomerAddress");
        customer.setCorporation(true);
        customer.setCorporateName("TestCustomerCorporateName");
        customer.setSiren("TestCustomerSiren");
        customer.setSiret("TestCustomerSiret");
        customer.setRib("TestCustomerRib");
        customer.setRcsInteger(123456);
        customerRepository.save(customer);

        try {
            // Test de récupération de la liste des clients
            List<CustomerEntity> customers = customerService.getCustomers();

            // Vérifie si la liste n'est pas vide
            assertFalse(customers.isEmpty());
        } finally {
            // Remove the created customer
            customerRepository.deleteAll();
        }
    }

    @Test
    void deleteCustomer_ExistingId_DeletesCustomer() {
        // Crée un client pour le test
        CustomerEntity customer = new CustomerEntity();
        customer.setName("TestCustomer");
        CustomerEntity savedCustomer = customerRepository.save(customer);

        try {
            // Supprime le client créée
            customerService.deleteCustomer(savedCustomer.getId());

            // Vérifie si le client a été supprimé de la base de données
            assertFalse(customerRepository.existsById(savedCustomer.getId()));
        } finally {
            // Remove the created customer
            customerRepository.deleteAll();
        }
    }

    @Test
    void deleteCustomer_NullId_ThrowsAPIException() {
        assertThrows(APIException.class, () -> customerService.deleteCustomer(null));
    }

    @Test
    void updateCustomer_ExistingCustomer_ReturnsUpdatedCustomer() {
        // Crée un client pour le test
        CustomerEntity customer = new CustomerEntity();
        customer.setName("TestCustomer");
        CustomerEntity savedCustomer = customerRepository.save(customer);

        try {
            // Met à jour le client
            savedCustomer.setName("UpdatedCustomer");
            CustomerEntity updatedCustomer = customerService.updateCustomer(savedCustomer);

            // Vérifie si le client a été mis à jour correctement
            assertEquals("UpdatedCustomer", updatedCustomer.getName());
        } finally {
            // Remove the created customer
            customerRepository.deleteAll();
        }
    }

    @Test
    void updateCustomer_NonExistingCustomer_ThrowsAPIException() {
        // Crée un client avec un ID inexistant
        CustomerEntity customer = new CustomerEntity();
        customer.setId(999L); // ID inexistant
        customer.setName("TestCustomer");

        // Vérifie si une APIException est levée lors de la tentative de mise à jour d'un client inexistant
        assertThrows(APIException.class, () -> customerService.updateCustomer(customer));
    }

    @Test
    void getCustomer_ExistingId_ReturnsCustomer() {
        // Crée un client pour le test
        CustomerEntity customer = new CustomerEntity();
        customer.setName("TestCustomer");
        customer.setAddress("TestCustomerAddress");
        customer.setCorporation(true);
        customer.setCorporateName("TestCustomerCorporateName");
        customer.setSiren("TestCustomerSiren");
        customer.setSiret("TestCustomerSiret");
        customer.setRib("TestCustomerRib");
        customer.setRcsInteger(123456);
        CustomerEntity savedCustomer = customerRepository.save(customer);

        try {
            // Récupère le client par son ID
            CustomerEntity retrievedCustomer = customerService.getCustomer(savedCustomer.getId());

            // Vérifie si le client récupéré est le même que celui enregistré
            assertEquals(savedCustomer.getId(), retrievedCustomer.getId());
            assertEquals(savedCustomer.getName(), retrievedCustomer.getName());
        } finally {
            // Remove the created customer
            customerRepository.deleteAll();
        }
    }

    @Test
    void getCustomer_NonExistingId_ThrowsAPIException() {
        // Vérifie si une APIException est levée lors de la tentative de récupération d'un client avec un ID inexistant
        assertThrows(APIException.class, () -> customerService.getCustomer(999L)); // ID inexistant
    }
}