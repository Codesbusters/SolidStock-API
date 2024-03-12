package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByName(String name);
}