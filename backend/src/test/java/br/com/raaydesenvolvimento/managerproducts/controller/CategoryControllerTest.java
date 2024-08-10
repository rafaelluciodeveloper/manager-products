package br.com.raaydesenvolvimento.managerproducts.controller;

import br.com.raaydesenvolvimento.managerproducts.dto.CategoryRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.service.CategoryService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> expectedPage = new PageImpl<>(Arrays.asList(new Category(), new Category()));

        when(categoryService.findAllWithFilters(eq("001"), eq("test"), eq(pageable))).thenReturn(expectedPage);

        ResponseEntity<Page<Category>> response = categoryController.findAll("001", "test", pageable);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());

        verify(categoryService, times(1)).findAllWithFilters(eq("001"), eq("test"), eq(pageable));
    }

    @Test
    void findById_Success() {
        Category category = new Category();
        category.setId(1L);

        when(categoryService.findById(anyLong())).thenReturn(category);

        ResponseEntity<Category> response = categoryController.findById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void findById_NotFound() {
        when(categoryService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Category> response = categoryController.findById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void save_Success() {
        Category category = new Category();
        category.setId(1L);

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(1L);

        when(categoryService.save(categoryRequest)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.save(categoryRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(categoryService, times(1)).save(any(CategoryRequest.class));
    }

    @Test
    void update_Success() {
        Category existingCategory = new Category();
        existingCategory.setId(1L);

        Category updatedCategory = new Category();
        updatedCategory.setId(1L);

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(1L);

        when(categoryService.findById(anyLong())).thenReturn(existingCategory);
        when(categoryService.save(any(CategoryRequest.class))).thenReturn(updatedCategory);

        ResponseEntity<Category> response = categoryController.update(1L, categoryRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(categoryService, times(1)).findById(1L);
        verify(categoryService, times(1)).save(any(CategoryRequest.class));
    }

    @Test
    void update_NotFound() {
        CategoryRequest updatedCategory = new CategoryRequest();
        updatedCategory.setId(1L);

        when(categoryService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Category> response = categoryController.update(1L, updatedCategory);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(categoryService, times(1)).findById(1L);
        verify(categoryService, times(0)).save(any(CategoryRequest.class));
    }

    @Test
    void deleteById_Success() {
        ResponseEntity<Void> response = categoryController.deleteById(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(categoryService, times(1)).deleteById(1L);
    }

}
