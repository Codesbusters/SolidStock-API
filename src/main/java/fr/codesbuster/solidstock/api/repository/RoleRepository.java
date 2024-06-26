package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);

    Optional<RoleEntity> findById(long id);
}