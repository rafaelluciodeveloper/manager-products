package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.dto.RegisterUserRequest;
import br.com.raaydesenvolvimento.managerproducts.model.User;
import br.com.raaydesenvolvimento.managerproducts.model.enums.Role;
import br.com.raaydesenvolvimento.managerproducts.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("testuser");
        registerUserRequest.setPassword("password");
        registerUserRequest.setRole(Role.VIEW);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setPassword("password");
        savedUser.setRole(Role.VIEW);
        savedUser.setEnabled(true);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = authService.registerUser(registerUserRequest);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals(Role.VIEW, result.getRole());
        assertTrue(result.isEnabled());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
