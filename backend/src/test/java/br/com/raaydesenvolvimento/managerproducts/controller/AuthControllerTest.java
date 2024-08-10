package br.com.raaydesenvolvimento.managerproducts.controller;

import br.com.raaydesenvolvimento.managerproducts.config.JwtTokenUtil;
import br.com.raaydesenvolvimento.managerproducts.dto.JwtRequest;
import br.com.raaydesenvolvimento.managerproducts.dto.JwtResponse;
import br.com.raaydesenvolvimento.managerproducts.dto.RegisterUserRequest;
import br.com.raaydesenvolvimento.managerproducts.model.User;
import br.com.raaydesenvolvimento.managerproducts.model.enums.Role;
import br.com.raaydesenvolvimento.managerproducts.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        request.setRole(Role.VIEW);

        User savedUser = new User();
        savedUser.setUsername("testuser");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(Role.VIEW);
        savedUser.setEnabled(true);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(authService.registerUser(any(RegisterUserRequest.class))).thenReturn(savedUser);

        User result = authController.registerUser(request);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(authService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    void createAuthenticationToken_Success() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("testuser");
        jwtRequest.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userDetails.getPassword()).thenReturn("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("mockToken");

        ResponseEntity<?> response = authController.createAuthenticationToken(jwtRequest);

        assertNotNull(response);
        assertInstanceOf(JwtResponse.class, response.getBody());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("mockToken", jwtResponse.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(jwtTokenUtil, times(1)).generateToken(userDetails);
    }

    @Test
    void createAuthenticationToken_BadCredentials() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("testuser");
        jwtRequest.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid Credentials"));

        Exception exception = assertThrows(Exception.class, () -> authController.createAuthenticationToken(jwtRequest));

        assertEquals("Invalid Credentials", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void createAuthenticationToken_DisabledUser() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("disabledUser");
        jwtRequest.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("User Disabled"));

        Exception exception = assertThrows(Exception.class, () -> {
            authController.createAuthenticationToken(jwtRequest);
        });

        assertEquals("User Disabled", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}