package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.dto.CategoryRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.repository.CategoryRepository;
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

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> expectedPage = new PageImpl<>(Arrays.asList(new Category(), new Category()));

        when(categoryRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Category> result = categoryService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_Success() {
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Category result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void findById_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Category result = categoryService.findById(1L);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void save_Success() {
        CategoryRequest category = new CategoryRequest();
        category.setId(1L);

        Category categoryEntity = new Category();
        categoryEntity.setId(1L);

        when(categoryRepository.save(any(Category.class))).thenReturn(categoryEntity);

        Category result = categoryService.save(category);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteById_Success() {
        doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

}
