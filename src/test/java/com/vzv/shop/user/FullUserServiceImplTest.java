package com.vzv.shop.user;

import com.vzv.shop.DataForTests;
import com.vzv.shop.entity.user.FullUser;
import com.vzv.shop.repository.FullUserRepository;
import com.vzv.shop.service.implementation.FullUserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FullUserServiceImplTest {

    @Mock
    private FullUserRepository userRepository;

    @InjectMocks
    private FullUserServiceImpl fullUserService;

    private final DataForTests data = new DataForTests();


    @Test
    void testGetAll() {
        List<FullUser> expectedResult = data.getFullUserList();

        when(userRepository.findAll()).thenReturn(expectedResult);
        List<FullUser> actualResult = fullUserService.getAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetById() {
        FullUser expectedResult = data.getFullUserList().get(0);

        when(userRepository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        FullUser actualResult = fullUserService.getById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSave() {
        FullUser expectedResult = data.getFullUserList().get(0);

        when(userRepository.save(expectedResult)).thenReturn(expectedResult);
        FullUser actualResult = fullUserService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdate() {
        FullUser expectedResult = data.getFullUserList().get(0);

        when(userRepository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        when(userRepository.save(expectedResult)).thenReturn(expectedResult);
        FullUser actualResult = fullUserService.update(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDelete() {
        FullUser user = data.getFullUserList().get(0);
        boolean expectedResult = true;

        doNothing().when(userRepository).deleteById(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(null));
        boolean actualResult = fullUserService.delete(user.getId());

        assertEquals(expectedResult, actualResult);
    }
}