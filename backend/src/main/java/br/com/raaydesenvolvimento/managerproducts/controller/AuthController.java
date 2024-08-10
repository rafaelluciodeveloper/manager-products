package br.com.raaydesenvolvimento.managerproducts.controller;

import br.com.raaydesenvolvimento.managerproducts.config.JwtTokenUtil;
import br.com.raaydesenvolvimento.managerproducts.dto.JwtRequest;
import br.com.raaydesenvolvimento.managerproducts.dto.JwtResponse;
import br.com.raaydesenvolvimento.managerproducts.dto.RegisterUserRequest;
import br.com.raaydesenvolvimento.managerproducts.model.User;
import br.com.raaydesenvolvimento.managerproducts.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "1. Authentication", description = "Endpoints de autenticação")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService,
                          AuthService authService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody RegisterUserRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User Disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }
    }
}
