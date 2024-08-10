package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.model.User;
import br.com.raaydesenvolvimento.managerproducts.model.enums.Role;
import br.com.raaydesenvolvimento.managerproducts.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserFound() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRole(Role.VIEW);

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_VIEW")));

        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("unknownuser"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("unknownuser");
    }
}
