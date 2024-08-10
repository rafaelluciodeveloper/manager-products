package br.com.raaydesenvolvimento.managerproducts.repository;

import br.com.raaydesenvolvimento.managerproducts.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE " +
            "(:code IS NULL OR c.code LIKE %:code%) AND " +
            "(:description IS NULL OR c.description LIKE %:description%)")
    Page<Category> findByFilters(@Param("code") String code,
                                 @Param("description") String description,
                                 Pageable pageable);

    Category findByCode(String code);

}
