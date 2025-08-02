package com.vzv.shop.order;

import com.vzv.shop.entity.order.Order;
import com.vzv.shop.repository.ContactRepository;
import com.vzv.shop.repository.OrderRepository;
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
        classes = { OrderRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/create_new_schema.sql", "/sql/create_new_tables.sql", "/sql/fill_testdata.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    void testGetOrderById() {
        Order actualResult =  repository.getOrderById("K323klkm43NN43").orElse(null);

        assertNotNull(actualResult);
        assertNotNull(actualResult.getCustomer());
        assertNotNull(actualResult.getConstructors());
        assertNotNull(actualResult.getOrderLines());
        assertFalse(actualResult.getConstructors().isEmpty());
        assertFalse(actualResult.getOrderLines().isEmpty());
    }

    @Test
    void testFindAllByCustomerId() {
        int actualResult =  repository.findAllByCustomerId("TestUser1").size();

        int expectedResult = 2;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByCreated() {
        int actualResult =  repository.findAllByCreated("23.07.2025").size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByUpdated() {
        int actualResult = repository.findAllByUpdated("24.07.2025").size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByStatus() {
        int actualResult = repository.findAllByStatus("PROCESSING").size();

        int expectedResult = 2;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCountByStatus() {
        int actualResult = repository.getCountByStatus("PROCESSING");

        int expectedResult = 2;

        assertEquals(expectedResult, actualResult);
    }
}