package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.dto.ProductRequest;
import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.model.Product;
import br.com.raaydesenvolvimento.managerproducts.repository.CategoryRepository;
import br.com.raaydesenvolvimento.managerproducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(ProductRequest product) {
        Product newProduct = new Product();
        newProduct.setId(product.getId());
        newProduct.setCode(product.getCode());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        Category category  = categoryRepository.findById(product.getCategoryId()).orElse(null);
        newProduct.setCategory(category);
        return productRepository.save(newProduct);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findAllWithFilters(String code, String description, Double minPrice, Double maxPrice, Long categoryId, Pageable pageable) {
        return productRepository.findByFilters(code, description, minPrice, maxPrice, categoryId, pageable);
    }
}
