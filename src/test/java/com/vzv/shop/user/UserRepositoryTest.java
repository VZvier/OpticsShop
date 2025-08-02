package com.vzv.shop.user;

import com.vzv.shop.entity.user.User;
import com.vzv.shop.enumerated.Role;
import com.vzv.shop.repository.ContactRepository;
import com.vzv.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = { UserRepository.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/create_new_schema.sql", "/sql/create_new_tables.sql", "/sql/fill_testdata.sql" },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserRepositoryTest {


    @Autowired
    private UserRepository repository;

    @Test
    void testFindUserByLogin() {
        User actualResult = repository.findUserByLogin("Ivanov00").orElse(null);

        User expectedResult = new User("TestUser1", "Ivanov00",
                "$2a$10$jOxNUIGSxaQ.NoFIfdJrFu4.OO2OOUS0PPX8lbtUq40mIXPIywzXy", Set.of(Role.USER),
                "21.05.2025", "21.05.2025");

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testExistsByLogin() {
        boolean actualResult = repository.existsByLogin("Ivanov00");

        assertTrue(actualResult);
    }

    @Test
    void testFindUsersByLoginOrIdSubstring() {
        List<String> actualResult = repository.findUsersByLoginOrIdSubstring("Iva")
                .stream().map(String::strip).toList();

        List<String> expectedResult = List.of("TestUser1");

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }
}