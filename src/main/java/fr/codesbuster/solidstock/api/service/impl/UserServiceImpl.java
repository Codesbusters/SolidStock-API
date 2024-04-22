package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<UserEntity> createUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        if (userRepository.existsByCustomer_Id(registerDto.getCustomerId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Customer already has a user");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setLastName(registerDto.getLastName());
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userEntity.setLanguage(registerDto.getLanguage());
        CustomerEntity customerEntity = customerRepository.findById(registerDto.getCustomerId()).orElse(null);
        userEntity.setCustomer(customerEntity);

        // Créer une nouvelle liste de rôles pour l'utilisateur
        List<RoleEntity> roles = new ArrayList<>();

        roles.add(roleRepository.findByName("USER").orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Default Role not found")));

        // Définir la liste de rôles pour l'utilisateur
        userEntity.setRoles(roles);

        // Enregistrer l'utilisateur dans la base de données
        userEntity = userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }


    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User id cannot be null");
        }

        if (!userRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User does not exist");
        }
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<UserEntity> updateUser(long id, RegisterDto registerDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));

        userEntity.setLastName(registerDto.getLastName() != null ? registerDto.getLastName() : userEntity.getLastName());
        userEntity.setFirstName(registerDto.getFirstName() != null ? registerDto.getFirstName() : userEntity.getFirstName());
        userEntity.setEmail(registerDto.getEmail() != null ? registerDto.getEmail() : userEntity.getEmail());
        userEntity.setPassword(registerDto.getPassword() != null ? passwordEncoder.encode(registerDto.getPassword()) : userEntity.getPassword());
        userEntity.setCustomer(registerDto.getCustomerId() != 0 ? customerRepository.findById(registerDto.getCustomerId()).orElse(null) : userEntity.getCustomer());
        userEntity.setDefaultPage(registerDto.getDefaultPage() != null ? registerDto.getDefaultPage() : userEntity.getDefaultPage());
        userEntity.setLanguage(registerDto.getLanguage() != null ? registerDto.getLanguage() : userEntity.getLanguage());

        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }


    @Override
    public ResponseEntity<UserEntity> addRoleToUser(long id, long roleId) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));

        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));

        userEntity.getRoles().add(roleEntity);
        userEntity = userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }


    @Override
    public ResponseEntity<UserEntity> removeRoleFromUser(long id, long roleId) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));

        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));

        userEntity.getRoles().remove(roleEntity);
        userEntity = userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }


    @Override
    public ResponseEntity<UserEntity> getUser(long id) {
        return ResponseEntity.ok(userRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found")));
    }
}
