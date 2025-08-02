package com.vzv.shop.user;

import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.repository.ContactRepository;
import com.vzv.shop.repository.CustomerRepository;
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
        classes = { CustomerRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/create_new_schema.sql", "/sql/create_new_tables.sql", "/sql/fill_testdata.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = repository.getAllCustomers();
        int actualResult = customers.size();

        int expectedResult = 5;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByNameSubstring() {
        List<Customer> customers = repository.findAllByNameSubstring("Iva");
        int actualResult = customers.size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByNameWithTwoArgs() {
        List<Customer> customers = repository.findAllByName("Ivanov", "Ivan");
        int actualResult = customers.size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindAllByNameWithThreeArgs() {
        List<Customer> customers = repository.findAllByName("Ivanov", "Ivan", "Ivanovich");
        int actualResult = customers.size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void searchByCriteria() {
        List<Customer> customers = repository.searchByCriteria("ivanov00");
        int actualResult = customers.size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);

        customers = repository.searchByCriteria("+38(0");
        actualResult = customers.size();

        expectedResult = 5;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findByFullName() {
        List<Customer> customers = repository.findByFullName("Ivanov", "Ivan", "Ivanovich");
        int actualResult = customers.size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findMatches() {
        List<Customer> customers = repository.findMatches("Ivan");
        int actualResult = customers.size();

        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);

        customers = repository.findMatches("petr");
        actualResult = customers.size();

        assertEquals(expectedResult, actualResult);

        customers = repository.findMatches("+38(063)");
        actualResult = customers.size();

        assertEquals(expectedResult, actualResult);
    }
}