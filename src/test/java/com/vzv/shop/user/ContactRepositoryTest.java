package com.vzv.shop.user;

import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.repository.ContactRepository;
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
        classes = { ContactRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/create_new_schema.sql", "/sql/create_new_tables.sql", "/sql/fill_testdata.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ContactRepositoryTest {

    @Autowired
    private  ContactRepository repository;

    @Test
    void findContactByEmail() {
        Contact actualResult = repository.findContactByEmail("ivanov00@gmail.com").orElse(null);

        Contact expectedResult = new Contact("TestUser1           ", "ivanov00@gmail.com  ", "+38(097)440-92-85   ");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findContactByPhone() {
        Contact actualResult = repository.findContactByPhone("+38(097)440-92-85").orElse(null);

        Contact expectedResult = new Contact("TestUser1           ", "ivanov00@gmail.com  ", "+38(097)440-92-85   ");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void existsByEmail() {
        Contact actualResult = repository.findContactByEmail("ivanov00@gmail.com").orElse(null);

        Contact expectedResult = new Contact("TestUser1           ", "ivanov00@gmail.com  ", "+38(097)440-92-85   ");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void existsByPhone() {
        Contact actualResult = repository.findContactByPhone("+38(097)440-92-85").orElse(null);

        Contact expectedResult = new Contact("TestUser1           ", "ivanov00@gmail.com  ", "+38(097)440-92-85   ");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findContactsByContactSubstring() {
        List<Contact> actualResult = repository.findContactsByContactSubstring("+38(097)");

        List<Contact> expectedResult = List.of(new Contact("TestUser1           ", "ivanov00@gmail.com  ", "+38(097)440-92-85   "));

        assertEquals(expectedResult, actualResult);
    }
}