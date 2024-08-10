package br.com.raaydesenvolvimento.managerproducts.controller;


import br.com.raaydesenvolvimento.managerproducts.dto.ProductRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Product;
import br.com.raaydesenvolvimento.managerproducts.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Tag(name = "3. Products", description = "Gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('VIEW', 'EDIT')")
    public ResponseEntity<Page<Product>> findAll(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            Pageable pageable
    ) {
        Page<Product> products = productService.findAllWithFilters(code, description, minPrice, maxPrice, categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VIEW', 'EDIT')")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('EDIT')")
    public ResponseEntity<Product> save(@Valid @RequestBody ProductRequest product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EDIT')")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductRequest updatedProduct) {
        Product existingProduct = productService.findById(id);
        if (existingProduct != null) {
            updatedProduct.setId(id);
            return ResponseEntity.ok(productService.save(updatedProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EDIT')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
