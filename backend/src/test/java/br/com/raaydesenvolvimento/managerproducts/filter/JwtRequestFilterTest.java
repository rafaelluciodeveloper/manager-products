package br.com.raaydesenvolvimento.managerproducts.filter;

import br.com.raaydesenvolvimento.managerproducts.config.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class JwtRequestFilterTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void doFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "Bearer validToken";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtil.getUsernameFromToken("validToken")).thenReturn(username);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtTokenUtil.validateToken("validToken", userDetails)).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtTokenUtil, times(1)).validateToken("validToken", userDetails);
        verify(chain, times(1)).doFilter(request, response);
        assert(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
    }

    @Test
    void doFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtTokenUtil, never()).validateToken(anyString(), any());
        verify(chain, times(1)).doFilter(request, response);
        assert(SecurityContextHolder.getContext().getAuthentication() == null);
    }

    @Test
    void doFilterInternal_SwaggerAndAuthPath() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/swagger-ui.html");

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assert(SecurityContextHolder.getContext().getAuthentication() == null);
    }
}

