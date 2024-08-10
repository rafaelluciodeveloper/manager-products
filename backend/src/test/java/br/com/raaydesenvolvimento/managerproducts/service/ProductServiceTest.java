package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.dto.ProductRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.model.Product;
import br.com.raaydesenvolvimento.managerproducts.repository.CategoryRepository;
import br.com.raaydesenvolvimento.managerproducts.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> expectedPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));

        when(productRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Product> result = productService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_Success() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findById_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Product result = productService.findById(1L);

        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void save_Success() {
        Product product = new Product();
        product.setId(1L);

        ProductRequest productRequest = new ProductRequest();
        product.setId(1L);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.save(productRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteById_Success() {
        doNothing().when(productRepository).deleteById(anyLong());

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

}
