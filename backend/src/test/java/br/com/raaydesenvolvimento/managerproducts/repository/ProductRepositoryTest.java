package br.com.raaydesenvolvimento.managerproducts.repository;

import br.com.raaydesenvolvimento.managerproducts.model.Category;
import br.com.raaydesenvolvimento.managerproducts.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByFilters_AllFilters_Success() {
        Category category1 = new Category();
        category1.setCode("CAT001");
        category1.setDescription("Electronics");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setCode("CAT002");
        category2.setDescription("Books");
        categoryRepository.save(category2);

        Product product1 = new Product();
        product1.setCode("PROD001");
        product1.setDescription("Smartphone");
        product1.setPrice(BigDecimal.valueOf(500.0));
        product1.setCategory(category1);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setCode("PROD002");
        product2.setDescription("Laptop");
        product2.setPrice(BigDecimal.valueOf(1500.0));
        product2.setCategory(category1);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setCode("PROD003");
        product3.setDescription("Novel");
        product3.setPrice(BigDecimal.valueOf(15.0));
        product3.setCategory(category2);
        productRepository.save(product3);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> result = productRepository.findByFilters("PROD001", null, null, null, null, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("PROD001", result.getContent().get(0).getCode());

        result = productRepository.findByFilters(null, "Laptop", null, null, null, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop", result.getContent().get(0).getDescription());

        result = productRepository.findByFilters(null, null, 100.0, 1000.0, null, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Smartphone", result.getContent().get(0).getDescription());

        result = productRepository.findByFilters(null, null, null, null, category1.getId(), pageable);
        assertEquals(2, result.getTotalElements());

        result = productRepository.findByFilters("PROD002", "Laptop", 1000.0, 2000.0, category1.getId(), pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("PROD002", result.getContent().get(0).getCode());

        result = productRepository.findByFilters(null, null, null, null, null, pageable);
        assertEquals(3, result.getTotalElements());
    }
}
