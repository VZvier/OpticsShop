package com.vzv.shop.settlements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.rest.SettlementAndStreetController;
import com.vzv.shop.entity.sattlemants.City;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.sattlemants.Street;
import com.vzv.shop.service.SettlementService;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(SettlementAndStreetController.class)
@Import(ApplicationConfig.class)
class SettlementAndStreetControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private SettlementService service;


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithUser
    void testSaveRegionsWithWithUser() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Regions were saved!";
        List<Region> regions = List.of(
                new Region("od", "Одесса", "Одеса", "Odessa"),
                new Region("he", "Херсон", "Херсон", "Kherson")
            );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/regions/new");
        mockReq.param("regions[]", mapper.writeValueAsString(regions));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveRegions(anyList())).thenReturn(regions);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testSaveRegionsWithWithMockStaff() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Regions were saved!";
        List<Region> regions = List.of(
                new Region("od", "Одесса", "Одеса", "Odessa"),
                new Region("he", "Херсон", "Херсон", "Kherson")
            );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/regions/new");
        mockReq.param("regions[]", mapper.writeValueAsString(regions));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveRegions(anyList())).thenReturn(regions);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testSaveRegionsWithWithMockSysadmin() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Regions were saved!";
        List<Region> regions = List.of(
                new Region("od", "Одесса", "Одеса", "Odessa"),
                new Region("he", "Херсон", "Херсон", "Kherson")
            );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/regions/new");
        mockReq.param("regions[]", mapper.writeValueAsString(regions));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveRegions(anyList())).thenReturn(regions);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testSaveRegionsWithMockAll() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Regions were saved!";
        List<Region> regions = List.of(
                new Region("od", "Одесса", "Одеса", "Odessa"),
                new Region("he", "Херсон", "Херсон", "Kherson")
            );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/regions/new");
        mockReq.param("regions[]", mapper.writeValueAsString(regions));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveRegions(anyList())).thenReturn(regions);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testSaveCitiesWithUser() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Cities were saved!";
        List<City> cities = List.of(
                new City("od", "Odessa", "Одесса", "Одеса"),
                new City("he", "Kherson", "Херсон", "Херсон")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/cities/new");
        mockReq.param("cities[]", mapper.writeValueAsString(cities));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveCities(anyList())).thenReturn(cities);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testSaveCitiesWithMockStaff() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Cities were saved!";
        List<City> cities = List.of(
                new City("od", "Odessa", "Одесса", "Одеса"),
                new City("he", "Kherson", "Херсон", "Херсон")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/cities/new");
        mockReq.param("cities[]", mapper.writeValueAsString(cities));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveCities(anyList())).thenReturn(cities);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testSaveCitiesWithWithMockSysadmin() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Cities were saved!";
        List<City> cities = List.of(
                new City("od", "Odessa", "Одесса", "Одеса"),
                new City("he", "Kherson", "Херсон", "Херсон")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/cities/new");
        mockReq.param("cities[]", mapper.writeValueAsString(cities));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveCities(anyList())).thenReturn(cities);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testSaveCitiesWithMockAll() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Cities were saved!";
        List<City> cities = List.of(
                new City("od", "Odessa", "Одесса", "Одеса"),
                new City("he", "Kherson", "Херсон", "Херсон")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/cities/new");
        mockReq.param("cities[]", mapper.writeValueAsString(cities));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveCities(anyList())).thenReturn(cities);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testSaveStreetsWithUser() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Streets were saved!";
        List<Street> streets = List.of(
                new Street("fFAfa", "od", "Odessa", "Одесса", "Одеса"),
                new Street("SGSsGS", "kh", "Cherkas'ka", "ул. Черкасская", "вул. Черкасська")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/streets/new");
        mockReq.param("streets[]", mapper.writeValueAsString(streets));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveStreets(anyList())).thenReturn(streets);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testSaveStreetsWithMockStaff() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Streets were saved!";
        List<Street> streets = List.of(
                new Street("fFAfa", "od", "Odessa", "Одесса", "Одеса"),
                new Street("SGSsGS", "kh", "Cherkas'ka", "ул. Черкасская", "вул. Черкасська")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/streets/new");
        mockReq.param("streets[]", mapper.writeValueAsString(streets));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveStreets(anyList())).thenReturn(streets);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testSaveStreetsWithMockSysadmin() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Streets were saved!";
        List<Street> streets = List.of(
                new Street("fFAfa", "od", "Odessa", "Одесса", "Одеса"),
                new Street("SGSsGS", "kh", "Cherkas'ka", "ул. Черкасская", "вул. Черкасська")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/streets/new");
        mockReq.param("streets[]", mapper.writeValueAsString(streets));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveStreets(anyList())).thenReturn(streets);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testSaveStreetsWithMockAll() throws Exception {
        int expectedStatus = 200;
        String expectedResult = "Streets were saved!";
        List<Street> streets = List.of(
                new Street("fFAfa", "od", "Odessa", "Одесса", "Одеса"),
                new Street("SGSsGS", "kh", "Cherkas'ka", "ул. Черкасская", "вул. Черкасська")
        );

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/shop/api/streets/new");
        mockReq.param("streets[]", mapper.writeValueAsString(streets));
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(service.saveStreets(anyList())).thenReturn(streets);

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String actualResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }
}