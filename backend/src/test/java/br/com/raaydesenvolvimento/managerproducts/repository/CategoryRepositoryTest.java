package br.com.raaydesenvolvimento.managerproducts.repository;

import br.com.raaydesenvolvimento.managerproducts.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByFilters_CodeAndDescription_Success() {
        Category category1 = new Category();
        category1.setCode("001");
        category1.setDescription("Electronics");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setCode("002");
        category2.setDescription("Books");
        categoryRepository.save(category2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Category> result = categoryRepository.findByFilters("001", null, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("001", result.getContent().get(0).getCode());

        result = categoryRepository.findByFilters(null, "Books", pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Books", result.getContent().get(0).getDescription());

        result = categoryRepository.findByFilters("002", "Books", pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("002", result.getContent().get(0).getCode());
        assertEquals("Books", result.getContent().get(0).getDescription());

        result = categoryRepository.findByFilters(null, null, pageable);
        assertEquals(2, result.getTotalElements());
    }
}
