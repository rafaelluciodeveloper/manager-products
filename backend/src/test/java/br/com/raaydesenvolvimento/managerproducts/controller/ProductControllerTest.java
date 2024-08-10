package br.com.raaydesenvolvimento.managerproducts.controller;

import br.com.raaydesenvolvimento.managerproducts.dto.ProductRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Product;
import br.com.raaydesenvolvimento.managerproducts.service.CategoryService;
import br.com.raaydesenvolvimento.managerproducts.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> expectedPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));

        when(productService.findAllWithFilters(eq("001"), eq("test"), eq(10.0), eq(100.0), eq(1L), eq(pageable)))
                .thenReturn(expectedPage);

        ResponseEntity<Page<Product>> response = productController.findAll("001", "test", 10.0, 100.0, 1L, pageable);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());

        verify(productService, times(1)).findAllWithFilters(eq("001"), eq("test"), eq(10.0), eq(100.0), eq(1L), eq(pageable));
    }

    @Test
    void findById_Success() {
        Product product = new Product();
        product.setId(1L);

        when(productService.findById(anyLong())).thenReturn(product);

        ResponseEntity<Product> response = productController.findById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void findById_NotFound() {
        when(productService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Product> response = productController.findById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).findById(1L);
    }

    @Test
    void save_Success() {
        Product product = new Product();
        product.setId(1L);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(1L);

        when(productService.save(productRequest)).thenReturn(product);

        ResponseEntity<Product> response = productController.save(productRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(productService, times(1)).save(any(ProductRequest.class));
    }

    @Test
    void update_Success() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);

        ProductRequest productRequest = new ProductRequest();
        updatedProduct.setId(1L);

        when(productService.findById(anyLong())).thenReturn(existingProduct);
        when(productService.save(productRequest)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.update(1L, productRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(productService, times(1)).findById(1L);
        verify(productService, times(1)).save(any(ProductRequest.class));
    }

    @Test
    void update_NotFound() {
        when(productService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Product> response = productController.update(1L, any(ProductRequest.class));

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).findById(1L);
        verify(productService, times(0)).save(any(ProductRequest.class));
    }

    @Test
    void deleteById_Success() {
        ResponseEntity<Void> response = productController.deleteById(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).deleteById(1L);
    }
}