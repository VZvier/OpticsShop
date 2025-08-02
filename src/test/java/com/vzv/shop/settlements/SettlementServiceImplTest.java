package com.vzv.shop.settlements;

import com.vzv.shop.DataForTests;
import com.vzv.shop.entity.sattlemants.City;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.sattlemants.Street;
import com.vzv.shop.repository.settlements.CityRepository;
import com.vzv.shop.repository.settlements.RegionRepository;
import com.vzv.shop.repository.settlements.StreetRepository;
import com.vzv.shop.service.implementation.SettlementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettlementServiceImplTest {

    @Mock
    private RegionRepository regionRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private StreetRepository streetRepository;

    @InjectMocks
    private SettlementServiceImpl settlementService;

    private final DataForTests data = new DataForTests();


    @Test
    void testGetRegions() {
        List<Region> regions = data.getRegionList();
        List<String> expectedResult = regions.stream().map(Region::getNameRu).toList();

        when(regionRepository.findAll()).thenReturn(regions);
        List<String> actualResult = settlementService.getRegions("Ru");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveRegions() {
        List<Region> expectedResult = data.getRegionList();

        when(regionRepository.saveAll(expectedResult)).thenReturn(expectedResult);
        List<Region> actualResult = settlementService.saveRegions(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveCities() {
        List<City> expectedResult = data.getCityList();

        when(cityRepository.saveAll(expectedResult)).thenReturn(expectedResult);
        List<City> actualResult = settlementService.saveCities(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveStreets() {
        List<Street> expectedResult = data.getStreetList();

        when(streetRepository.saveAll(expectedResult)).thenReturn(expectedResult);
        List<Street> actualResult = settlementService.saveStreets(expectedResult);

        assertEquals(expectedResult, actualResult);
    }
}