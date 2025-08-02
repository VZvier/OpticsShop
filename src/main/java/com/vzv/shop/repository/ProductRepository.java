package com.vzv.shop.repository;

import com.vzv.shop.entity.product.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @EntityGraph(attributePaths = {"pictures"})
    @NotNull
    List<Product> findAll();

    @Query("SELECT p.brand FROM Product p GROUP BY p.brand")
    List<String> findAllBrands();

    boolean existsById(@NotNull String id);

    @Query("SELECT p.nomination FROM Product p GROUP BY p.nomination")
    List<String> findAllNominations();
}
