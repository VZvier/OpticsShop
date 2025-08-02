package com.vzv.shop.repository;

import com.vzv.shop.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDtoRepository  extends JpaRepository<ProductDTO, String> {

    @Query("SELECT p.brand FROM ProductDTO p GROUP BY p.brand")
    List<String> getAllBrands();

    @Query("SELECT p.model FROM ProductDTO p WHERE p.model IS NOT NULL GROUP BY p.model")
    List<String> getAllModels();
    @Query("SELECT p.country FROM ProductDTO p WHERE p.country IS NOT NULL GROUP BY p.country")
    List<String> getAllCountries();

    @Query("SELECT p.model FROM ProductDTO p WHERE p.model IS NOT NULL AND p.brand = ?1 GROUP BY p.model")
    List<String> getAllModelsByBrand(String brand);

    @Query("SELECT p.description FROM ProductDTO p WHERE p.description IS NOT NULL GROUP BY p.description")
    List<String> getAllDescriptions();


    @Query("SELECT p.sp FROM ProductDTO p WHERE p.sp IS NOT NULL AND p.nomination = 'lens' GROUP BY p.sp")
    List<String> getAllLensDiopters();

    @Query("SELECT p.sp FROM ProductDTO p WHERE p.sp IS NOT NULL AND p.nomination = 'ready-glasses' " +
            "GROUP BY p.sp")
    List<String> getAllRGDiopters();

    @Query("SELECT p.cyl FROM ProductDTO p WHERE p.nomination = 'lens' GROUP BY p.cyl")
    List<String> getAllLensCylinders();

    @Query("SELECT p.distance FROM ProductDTO p WHERE p.nomination = 'ready-glasses' GROUP BY p.distance")
    List<String> getAllDistances();

    @Query("SELECT p.volume FROM ProductDTO p WHERE p.volume IS NOT NULL AND p.nomination = 'liquid' AND p.brand = ?1" +
            " GROUP BY p.volume")
    List<String> getAllLiquidVolumes(String brand);

    @Query("SELECT p.volume FROM ProductDTO p WHERE p.volume IS NOT NULL AND p.nomination = 'liquid' " +
            "GROUP BY p.volume")
    List<String> getAllLiquidVolumes();

    @Query("SELECT p.price FROM ProductDTO p GROUP BY  p.price")
    List<String> getAllPrices();

    @Query("SELECT p.coefficient FROM ProductDTO p WHERE p.coefficient IS NOT NULL GROUP BY p.coefficient")
    List<String> getAllCoefficients();

    @Query("SELECT p FROM ProductDTO p WHERE p.brand LIKE %?1% OR p.nomination LIKE %?1% OR p.model LIKE %?1%")
    List<ProductDTO> findAllByCriteria(String criteria);
}
