package com.vzv.shop.product;

import com.vzv.shop.repository.ProductDtoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = { ProductDtoRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/create_new_schema.sql", "/sql/create_new_tables.sql", "/sql/fill_testdata.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductDtoRepositoryTest {

    @Autowired
    private ProductDtoRepository repository;


    @Test
    void testGetAllBrands() {
        List<String> actualResult = repository.getAllBrands().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("BioMax", "Mien", "Tomotatsu", "OptyFree", "Morel", "CityCode",
                "BioLife", "Amshar");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllModels() {
        List<String> actualResult = repository.getAllModels().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("MSu-31-5432", "Mi-132-12", "Amr-4232 Coral", "Mo-43-41-2 blue",
                "Mi-31313 Dark", "SC-654-22");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllCountries() {
        List<String> actualResult = repository.getAllCountries().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("italy");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllModelsByBrand() {
        List<String> actualResult = repository.getAllModelsByBrand("Mien").stream().map(String::strip).toList();

        List<String> expectedResult = List.of("Mi-132-12", "Mi-31313 Dark");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllDescriptions() {
        List<String> actualResult = repository.getAllDescriptions().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("description");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllLensDiopters() {
        List<String> actualResult = repository.getAllLensDiopters().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("-0,25", "+1,00", "0");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllRGDiopters() {
        List<String> actualResult = repository.getAllRGDiopters().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("+1,25");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllLensCylinders() {
        List<String> actualResult = repository.getAllLensCylinders().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("-1,00", "+1,00", "0");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllDistances() {
        List<String> actualResult = repository.getAllDistances().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("62");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllLiquidVolumesByBrandName() {
        List<String> actualResult = repository.getAllLiquidVolumes("OptyFree").stream().map(String::strip).toList();

        List<String> expectedResult = List.of("300");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllLiquidVolumes() {
        List<String> actualResult = repository.getAllLiquidVolumes().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("300");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllPrices() {
        List<String> actualResult = repository.getAllPrices().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("450,00", "900,00", "2100,00", "300,00", "2000,00", "850,00",
                "780,00");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllCoefficients() {
        List<String> actualResult = repository.getAllCoefficients().stream().map(String::strip).toList();

        List<String> expectedResult = List.of("1,64", "1,53");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByCriteria() {
        int actualResult = repository.findAllByCriteria("Mien").size();

        int expectedResult = 2;

        assertEquals(expectedResult, actualResult);
    }
}