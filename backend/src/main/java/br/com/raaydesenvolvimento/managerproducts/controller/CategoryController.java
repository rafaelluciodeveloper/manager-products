package br.com.raaydesenvolvimento.managerproducts.controller;


import br.com.raaydesenvolvimento.managerproducts.dto.CategoryRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "2. Categories", description = "Gerenciamento de categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('VIEW', 'EDIT')")
    public ResponseEntity<Page<Category>> findAll(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description,
            Pageable pageable
    ) {
        Page<Category> categories = categoryService.findAllWithFilters(code, description, pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VIEW', 'EDIT')")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('EDIT')")
    public ResponseEntity<Category> save(@Valid @RequestBody CategoryRequest category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EDIT')")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest updatedCategory) {
        Category existingCategory = categoryService.findById(id);
        if (existingCategory != null) {
            updatedCategory.setId(id);
            return ResponseEntity.ok(categoryService.save(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EDIT')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

