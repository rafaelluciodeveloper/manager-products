package br.com.raaydesenvolvimento.managerproducts.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, String> errors = (Map<String, String>) responseEntity.getBody();
        assertEquals("defaultMessage", errors.get("field"));
    }

    @Test
    void handleBadCredentialsException() {
        BadCredentialsException ex = new BadCredentialsException("Bad credentials");

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleBadCredentialsException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, String> errorResponse = (Map<String, String>) responseEntity.getBody();
        assertEquals("Invalid credentials", errorResponse.get("error"));
    }

    @Test
    void handleDisabledException() {
        DisabledException ex = new DisabledException("User is disabled");

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleDisabledException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, String> errorResponse = (Map<String, String>) responseEntity.getBody();
        assertEquals("User is disabled", errorResponse.get("error"));
    }

    @Test
    void handleUsernameNotFoundException() {
        UsernameNotFoundException ex = new UsernameNotFoundException("User not found");

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleUsernameNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, String> errorResponse = (Map<String, String>) responseEntity.getBody();
        assertEquals("User not found", errorResponse.get("error"));
    }

    @Test
    void handleDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Data integrity violation",
                new org.hibernate.exception.ConstraintViolationException("Constraint violation", null, "constraintName"));

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, String> errorResponse = (Map<String, String>) responseEntity.getBody();
        assertEquals("Duplicate key value violates unique constraint", errorResponse.get("error"));
    }

    @Test
    void handleDataIntegrityViolationException_General() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Data integrity violation");

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertInstanceOf(Map.class, responseEntity.getBody());

        Map<String, String> errorResponse = (Map<String, String>) responseEntity.getBody();
        assertEquals("Data integrity violation", errorResponse.get("error"));
    }
}

