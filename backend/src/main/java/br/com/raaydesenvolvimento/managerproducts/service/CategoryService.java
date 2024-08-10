package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.dto.CategoryRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category save(CategoryRequest category) {
        Category newCategory = new Category();
        newCategory.setId(category.getId());
        newCategory.setCode(category.getCode());
        newCategory.setDescription(category.getDescription());
        return categoryRepository.save(newCategory);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Page<Category> findAllWithFilters(String code, String description, Pageable pageable) {
        return categoryRepository.findByFilters(code, description, pageable);
    }
}
