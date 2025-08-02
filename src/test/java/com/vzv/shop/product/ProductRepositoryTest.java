package com.vzv.shop.product;

import com.vzv.shop.entity.product.Product;
import com.vzv.shop.repository.ProductDtoRepository;
import com.vzv.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = { ProductRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/create_new_schema.sql", "/sql/create_new_tables.sql", "/sql/fill_testdata.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;


    @Test
    void testFindAll() {
        List<Product> products = repository.findAll();
        int actualResult = products.size();

        int expectedResult = 13;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllBrands() {
        List<String> actualResult = repository.findAllBrands();

        List<String> expectedResult = List.of("BioMax", "Mien", "Tomotatsu", "OptyFree", "Morel", "CityCode", "BioLife", "Amshar");

        assertEquals(expectedResult, actualResult.stream().map(String::strip).toList());
    }

    @Test
    void testExistsById() {
        boolean actualResult = repository.existsById("TestFrame1");

        boolean expectedResult = true;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllNominations() {
        List<String> actualResult = repository.findAllNominations();

        List<String> expectedResult = List.of("liquid", "medical equipment", "lens", "ready-glasses", "frame");

        assertEquals(expectedResult, actualResult.stream().map(String::strip).toList());
    }

}