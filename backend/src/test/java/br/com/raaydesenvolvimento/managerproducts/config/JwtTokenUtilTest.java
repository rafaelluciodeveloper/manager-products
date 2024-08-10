package br.com.raaydesenvolvimento.managerproducts.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.sql.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private Key secretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        secretKey = Keys.hmacShaKeyFor("bVBDWkZVV25OSGRGTW1oZ1FMa3ZPbXBuUlhIajI2RE1WR0ljTlBZbEFOWXZ2MGRmQjVUMWtHblBGbGQ2YnI0cA==".getBytes());
    }

    @Test
    void generateToken_Success() {
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_VIEW");
        UserDetails userDetails = new User("testuser", "password", authorities);

        String token = jwtTokenUtil.generateToken(userDetails);

        assertNotNull(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void getUsernameFromToken_Success() {
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_VIEW");
        UserDetails userDetails = new User("testuser", "password", authorities);
        String token = jwtTokenUtil.generateToken(userDetails);

        String username = jwtTokenUtil.getUsernameFromToken(token);

        assertEquals("testuser", username);
    }

    @Test
    void getExpirationDateFromToken_Success() {
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_VIEW");
        UserDetails userDetails = new User("testuser", "password", authorities);
        String token = jwtTokenUtil.generateToken(userDetails);

        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);

        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void validateToken_Success() {
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_VIEW");
        UserDetails userDetails = new User("testuser", "password", authorities);
        String token = jwtTokenUtil.generateToken(userDetails);

        Boolean isValid = jwtTokenUtil.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void validateToken_Failure_WrongUsername() {
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_VIEW");
        UserDetails userDetails = new User("testuser", "password", authorities);
        String token = jwtTokenUtil.generateToken(userDetails);

        UserDetails wrongUserDetails = new User("wronguser", "password", authorities);

        Boolean isValid = jwtTokenUtil.validateToken(token, wrongUserDetails);

        assertFalse(isValid);
    }

    @Test
    void validateToken_Failure_TokenExpired() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of("ROLE_VIEW"));
        claims.put(Claims.SUBJECT, "testuser");

        String expiredToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)) // Emitido há 24 horas
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 23)) // Expirou 23 horas atrás
                .signWith(secretKey)
                .compact();

        List<GrantedAuthority> authorities = List.of(() -> "ROLE_VIEW");
        UserDetails userDetails = new User("testuser", "password", authorities);

        assertThrows(ExpiredJwtException.class, () -> {
            jwtTokenUtil.validateToken(expiredToken, userDetails);
        });
    }
}
