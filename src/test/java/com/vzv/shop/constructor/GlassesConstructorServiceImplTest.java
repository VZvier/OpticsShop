package com.vzv.shop.constructor;

import com.vzv.shop.DataForTests;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import com.vzv.shop.repository.GlassesConstructorRepository;
import com.vzv.shop.service.implementation.GlassesConstructorServiceImpl;
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
class GlassesConstructorServiceImplTest {

    @Mock
    private GlassesConstructorRepository constrRepo;

    @InjectMocks
    private GlassesConstructorServiceImpl constrService;

    private final DataForTests data = new DataForTests();

    @Test
    void testGetAll() {
        List<GlassesConstructor> expectedResult = data.getConstructorList();

        when(constrRepo.findAll()).thenReturn(expectedResult);
        List<GlassesConstructor> actualResult = constrService.getAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetById() {
        GlassesConstructor expectedResult = data.getConstructorList().get(0);

        when(constrRepo.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        GlassesConstructor actualResult = constrService.getById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveAll() {
        List<GlassesConstructor> expectedResult = data.getConstructorList();

        when(constrRepo.saveAll(expectedResult)).thenReturn(expectedResult);
        List<GlassesConstructor> actualResult = constrService.saveAll(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSave() {
        GlassesConstructor expectedResult = data.getConstructorList().get(0);

        when(constrRepo.save(expectedResult)).thenReturn(expectedResult);
        GlassesConstructor actualResult = constrService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdate() {
        GlassesConstructor expectedResult = data.getConstructorList().get(0);

        when(constrRepo.save(expectedResult)).thenReturn(expectedResult);
        GlassesConstructor actualResult = constrService.update(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDelete() {
        GlassesConstructor constructor = data.getConstructorList().get(0);
        boolean expectedResult = true;

        doNothing().when(constrRepo).delete(constructor);
        boolean actualResult = constrService.delete(constructor);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteById() {
        GlassesConstructor constructor = data.getConstructorList().get(0);
        boolean expectedResult = true;

        doNothing().when(constrRepo).deleteById(constructor.getId());
        boolean actualResult = constrService.deleteById(constructor.getId());

        assertEquals(expectedResult, actualResult);
    }
}