package br.com.raaydesenvolvimento.managerproducts.repository;

import br.com.raaydesenvolvimento.managerproducts.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:code IS NULL OR p.code LIKE %:code%) AND " +
            "(:description IS NULL OR p.description LIKE %:description%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> findByFilters(@Param("code") String code,
                                @Param("description") String description,
                                @Param("minPrice") Double minPrice,
                                @Param("maxPrice") Double maxPrice,
                                @Param("categoryId") Long categoryId,
                                Pageable pageable);
}
