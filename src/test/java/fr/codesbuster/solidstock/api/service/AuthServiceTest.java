package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.LoginDto;
import fr.codesbuster.solidstock.api.payload.RegisterDto;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.security.JwtTokenProvider;
import fr.codesbuster.solidstock.api.service.impl.AuthServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class AuthServiceTest {


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;


    @BeforeEach
    void setUp() {
        if (roleRepository.findByName("USER").isEmpty()) {
            RoleEntity userRole = new RoleEntity();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login_ValidLoginDto_ReturnsToken() {
        // Mock AuthenticationManager
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Mock JwtTokenProvider
        String token = "mockToken";
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);

        // Test login method
        LoginDto loginDto = new LoginDto("username", "password");
        String resultToken = authService.login(loginDto);

        // Verify if the token is returned correctly
        assertEquals(token, resultToken);
    }

    @Test
    void register_ValidRegisterDto_UserRegisteredSuccessfully() {
        // Mock UserRepository
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);

        // Mock RoleRepository
        RoleEntity userRole = new RoleEntity();
        userRole.setName("USER");
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));

        // Mock PasswordEncoder
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);

        // Test register method
        RegisterDto registerDto = new RegisterDto("name", "username", "email", "password");
        String resultMessage = authService.register(registerDto);

        // Verify if the user is registered successfully
        assertEquals("User registered successfully!.", resultMessage);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void register_ExistingUsername_ThrowsAPIException() {
        // Mock UserRepository
        when(userRepository.existsByUsername(any())).thenReturn(true);

        // Test register method with existing username
        RegisterDto registerDto = new RegisterDto("name", "existingUsername", "email", "password");
        assertThrows(APIException.class, () -> authService.register(registerDto));
    }

    @Test
    void register_ExistingEmail_ThrowsAPIException() {
        // Mock UserRepository
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Test register method with existing email
        RegisterDto registerDto = new RegisterDto("name", "username", "existingEmail", "password");
        assertThrows(APIException.class, () -> authService.register(registerDto));
    }
}
