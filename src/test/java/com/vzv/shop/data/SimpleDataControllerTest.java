package com.vzv.shop.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.rest.SimpleDataController;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.product.Product;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.enumerated.FrameType;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.ProductService;
import com.vzv.shop.service.SettlementService;
import com.vzv.shop.service.UserService;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(SimpleDataController.class)
@Import(ApplicationConfig.class)
class SimpleDataControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private SettlementService settlementService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    private final DataForTests data = new DataForTests();

    private final String URL = "/shop/data/";

    MockedStatic<TXTReader> mockTxtReader;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
        mockTxtReader = mockStatic(TXTReader.class);
    }

    @AfterEach
    void tearDown() {
        mockTxtReader.close();
    }

    @Test
    @WithUser
    void testGetRegionsWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getRegionList().stream().map(Region::getNameRu).toList();

        when(settlementService.getRegions(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "regions"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetRegionsWithMockStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getRegionList().stream().map(Region::getNameRu).toList();

        when(settlementService.getRegions(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "regions"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetRegionsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getRegionList().stream().map(Region::getNameRu).toList();

        when(settlementService.getRegions(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "regions"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetRegionsWithMockAll() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getRegionList().stream().map(Region::getNameRu).toList();

        when(settlementService.getRegions(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "regions"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetEnRuCountryMapWithUser() throws Exception {
        int expectedStatus = 200;
        Map<String, String> expectedResult = CSVFileReader.COUNTRIES;;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "countries")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, String> actualResult = data.mapFromJson(content, new TypeReference<Map<String, String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetEnRuCountryMapWithStaff() throws Exception {
        int expectedStatus = 200;
        Map<String, String> expectedResult = CSVFileReader.COUNTRIES;;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "countries")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, String> actualResult = data.mapFromJson(content, new TypeReference<Map<String, String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetEnRuCountryMapWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Map<String, String> expectedResult = CSVFileReader.COUNTRIES;;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "countries")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, String> actualResult = data.mapFromJson(content, new TypeReference<Map<String, String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetEnRuCountryMapWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Map<String, String> expectedResult = CSVFileReader.COUNTRIES;;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "countries")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, String> actualResult = data.mapFromJson(content, new TypeReference<Map<String, String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetNominationsWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getNomination).toList();

        when(productService.getAllNominations()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "nominations")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetNominationsWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getNomination).toList();

        when(productService.getAllNominations()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "nominations")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetNominationsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getNomination).toList();

        when(productService.getAllNominations()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "nominations")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetNominationsWithMockAll() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getNomination).toList();

        when(productService.getAllNominations()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "nominations")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetBrandsWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getBrand).toList();

        when(productService.getAllBrands()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetBrandsWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getBrand).toList();

        when(productService.getAllBrands()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetBrandsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getBrand).toList();

        when(productService.getAllBrands()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetBrandsWithAll() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductList().stream().map(Product::getBrand).toList();

        when(productService.getAllBrands()).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetBrandsByNominationWithUser() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals("frame")).map(ProductDTO::getBrand).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getAll()).thenReturn(products);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands/frame")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetBrandsByNominationWithStaff() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals("frame")).map(ProductDTO::getBrand).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getAll()).thenReturn(products);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands/frame")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetBrandsByNominationWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals("frame")).map(ProductDTO::getBrand).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getAll()).thenReturn(products);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands/frame")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetBrandsByNominationWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals("frame")).map(ProductDTO::getBrand).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getAll()).thenReturn(products);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "brands/frame")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetModelsWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("Mien")).map(ProductDTO::getModel).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getBrandModels("Mien")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "models/Mien")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetModelsWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("Mien")).map(ProductDTO::getModel).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getBrandModels("Mien")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "models/Mien")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetModelsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("Mien")).map(ProductDTO::getModel).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getBrandModels("Mien")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "models/Mien")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetModelsWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("Mien")).map(ProductDTO::getModel).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getBrandModels("Mien")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "models/Mien"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetAllLiquidVolumesWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("OptyFree")).map(ProductDTO::getVolume).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getLiquidVolumes("OptyFree")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "volumes/OptyFree"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetAllLiquidVolumesWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("OptyFree")).map(ProductDTO::getVolume).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getLiquidVolumes("OptyFree")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "volumes/OptyFree"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetAllLiquidVolumesWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("OptyFree")).map(ProductDTO::getVolume).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getLiquidVolumes("OptyFree")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "volumes/OptyFree"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetAllLiquidVolumesWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getBrand().equals("OptyFree")).map(ProductDTO::getVolume).collect(Collectors.toSet())
                .stream().toList();

        when(productService.getLiquidVolumes("OptyFree")).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "volumes/OptyFree"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetStringByParameterWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = FrameType.getLabels();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "str-list/frameType"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetStringByParameterWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = FrameType.getLabels();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "str-list/frameType"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetStringByParameterWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = FrameType.getLabels();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "str-list/frameType"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetStringByParameterWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = FrameType.getLabels();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "str-list/frameType"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetIntsByParameterWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream().map(ProductDTO::getPrice)
                .collect(Collectors.toSet()).stream().toList();

        when(productService.getIntsByParameter("price", null)).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "int-list/price")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetIntsByParameterWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream().map(ProductDTO::getPrice)
                .collect(Collectors.toSet()).stream().toList();

        when(productService.getIntsByParameter("price", null)).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "int-list/price")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetIntsByParameterWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream().map(ProductDTO::getPrice)
                .collect(Collectors.toSet()).stream().toList();

        when(productService.getIntsByParameter("price", null)).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "int-list/price")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetIntsByParameterWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = data.getProductDTOList().stream().map(ProductDTO::getPrice)
                .collect(Collectors.toSet()).stream().toList();

        when(productService.getIntsByParameter("price", null)).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "int-list/price")).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testIsLoginFreeWithUser() throws Exception {
        int expectedStatus = 200;
        User user = data.getUserList().get(0);
        boolean expectedResult = false;


        when(userService.isLoginExists(user.getLogin())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "login/" + user.getLogin()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testIsLoginFreeWithStaff() throws Exception {
        int expectedStatus = 200;
        User user = data.getUserList().get(0);
        boolean expectedResult = false;


        when(userService.isLoginExists(user.getLogin())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "login/" + user.getLogin()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testIsLoginFreeWithSysadmin() throws Exception {
        int expectedStatus = 200;
        User user = data.getUserList().get(0);
        boolean expectedResult = false;


        when(userService.isLoginExists(user.getLogin())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "login/" + user.getLogin()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testIsLoginFreeWithAllRoles() throws Exception {
        int expectedStatus = 200;
        User user = data.getUserList().get(0);
        boolean expectedResult = false;


        when(userService.isLoginExists(user.getLogin())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "login/" + user.getLogin()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testIsEmailFreeWithUser() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isEmailExists(customer.getContact().getEmail())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "email/" + customer.getContact().getEmail()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testIsEmailFreeWithStaff() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isEmailExists(customer.getContact().getEmail())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "email/" + customer.getContact().getEmail()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testIsEmailFreeWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isEmailExists(customer.getContact().getEmail())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "email/" + customer.getContact().getEmail()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testIsEmailFreeWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isEmailExists(customer.getContact().getEmail())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "email/" + customer.getContact().getEmail()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testIsPhoneFreeWithUser() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isPhoneExists(customer.getContact().getPhone())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "phone/" + customer.getContact().getPhone()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testIsPhoneFreeWithStaff() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isPhoneExists(customer.getContact().getPhone())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "phone/" + customer.getContact().getPhone()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testIsPhoneFreeWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isPhoneExists(customer.getContact().getPhone())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "phone/" + customer.getContact().getPhone()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testIsPhoneFreeWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = false;


        when(userService.isPhoneExists(customer.getContact().getPhone())).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "phone/" + customer.getContact().getPhone()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Boolean actualResult = data.mapFromJson(content, Boolean.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetCustomersSearchSuggestionsWithUser() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = data.getCustomerList().stream()
                .map(Customer::getContact).map(Contact::getPhone)
                .filter(phone -> phone.contains(customer.getContact().getPhone().substring(0,4))).toList();

        when(userService.findCustomerData(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "customers/search/" + customer.getContact().getPhone().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetCustomersSearchSuggestionsWithStaff() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = data.getCustomerList().stream()
                .map(Customer::getContact).map(Contact::getPhone)
                .filter(phone -> phone.contains(customer.getContact().getPhone().substring(0,4))).toList();

        when(userService.findCustomerData(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "customers/search/" + customer.getContact().getPhone().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetCustomersSearchSuggestionsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = data.getCustomerList().stream()
                .map(Customer::getContact).map(Contact::getPhone)
                .filter(phone -> phone.contains(customer.getContact().getPhone().substring(0,4))).toList();

        when(userService.findCustomerData(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "customers/search/" + customer.getContact().getPhone().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetCustomersSearchSuggestionsWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = data.getCustomerList().stream()
                .map(Customer::getContact).map(Contact::getPhone)
                .filter(phone -> phone.contains(customer.getContact().getPhone().substring(0,4))).toList();

        when(userService.findCustomerData(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "customers/search/" + customer.getContact().getPhone().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetCustomersFullNameSuggestionsWithUser() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Customer> foundCustomers = data.getCustomerList().stream()
                .filter(c -> (c.getLName() + " " + c.getFName() + " " + c.getFatherName())
                        .contains(customer.getLName().substring(0,4))).toList();

        List<String> expectedResult = foundCustomers.stream().map(Customer::toStringFullName).toList();

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.multipart(HttpMethod.POST,
                URL +"/customers/search/full-name");
        mockReq.param("name", customer.getLName().substring(0,4));

        when(userService.getCustomersNamesByItsSubstring(anyString())).thenReturn(foundCustomers);

        MvcResult result = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetCustomersFullNameSuggestionsWithStaff() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Customer> foundCustomers = data.getCustomerList().stream()
                .filter(c -> (c.getLName() + " " + c.getFName() + " " + c.getFatherName())
                        .contains(customer.getLName().substring(0,4))).toList();

        List<String> expectedResult = foundCustomers.stream().map(Customer::toStringFullName).toList();

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.multipart(HttpMethod.POST,
                URL +"/customers/search/full-name");
        mockReq.param("name", customer.getLName().substring(0,4));

        when(userService.getCustomersNamesByItsSubstring(anyString())).thenReturn(foundCustomers);

        MvcResult result = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetCustomersFullNameSuggestionsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Customer> foundCustomers = data.getCustomerList().stream()
                .filter(c -> (c.getLName() + " " + c.getFName() + " " + c.getFatherName())
                        .contains(customer.getLName().substring(0,4))).toList();

        List<String> expectedResult = foundCustomers.stream().map(Customer::toStringFullName).toList();

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.multipart(HttpMethod.POST,
                URL +"/customers/search/full-name");
        mockReq.param("name", customer.getLName().substring(0,4));

        when(userService.getCustomersNamesByItsSubstring(anyString())).thenReturn(foundCustomers);

        MvcResult result = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetCustomersFullNameSuggestionsWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Customer> foundCustomers = data.getCustomerList().stream()
                .filter(c -> (c.getLName() + " " + c.getFName() + " " + c.getFatherName())
                        .contains(customer.getLName().substring(0,4))).toList();

        List<String> expectedResult = foundCustomers.stream().map(Customer::toStringFullName).toList();

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.multipart(HttpMethod.POST,
                URL +"/customers/search/full-name");
        mockReq.param("name", customer.getLName().substring(0,4));

        when(userService.getCustomersNamesByItsSubstring(anyString())).thenReturn(foundCustomers);

        MvcResult result = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetOrdersSearchSuggestionsWithUser() throws Exception {
        int expectedStatus = 200;
        List<Order> orders = data.getOrderList();
        List<String> expectedResult = orders.stream().map(Order::getId)
                .filter(id -> id.contains(orders.get(0).getId().substring(0,4))).toList();

        when(orderService.getSuggestionsByCriteria(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/search/" + orders.get(0).getId().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetOrdersSearchSuggestionsWithStaff() throws Exception {
        int expectedStatus = 200;
        List<Order> orders = data.getOrderList();
        List<String> expectedResult = orders.stream().map(Order::getId)
                .filter(id -> id.contains(orders.get(0).getId().substring(0,4))).toList();

        when(orderService.getSuggestionsByCriteria(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/search/" + orders.get(0).getId().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetOrdersSearchSuggestionsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<Order> orders = data.getOrderList();
        List<String> expectedResult = orders.stream().map(Order::getId)
                .filter(id -> id.contains(orders.get(0).getId().substring(0,4))).toList();

        when(orderService.getSuggestionsByCriteria(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/search/" + orders.get(0).getId().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetOrdersSearchSuggestionsWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<Order> orders = data.getOrderList();
        List<String> expectedResult = orders.stream().map(Order::getId)
                .filter(id -> id.contains(orders.get(0).getId().substring(0,4))).toList();

        when(orderService.getSuggestionsByCriteria(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/search/" + orders.get(0).getId().substring(0,4)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetGoodsSearchSuggestionsWithUser() throws Exception {
        int expectedStatus = 200;
        List<Product> products = data.getProductList();
        List<String> expectedResult = products.stream().map(Product::getNomination).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/nomination"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(Product::getBrand).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/brand"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        actualStatus = result.getResponse().getStatus();
        content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetGoodsSearchSuggestionsWithStaff() throws Exception {
        int expectedStatus = 200;
        List<Product> products = data.getProductList();
        List<String> expectedResult = products.stream().map(Product::getNomination).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/nomination"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(Product::getBrand).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/brand"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        actualStatus = result.getResponse().getStatus();
        content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetGoodsSearchSuggestionsWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<Product> products = data.getProductList();
        List<String> expectedResult = products.stream().map(Product::getNomination).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/nomination"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(Product::getBrand).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/brand"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        actualStatus = result.getResponse().getStatus();
        content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetGoodsSearchSuggestionsWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<Product> products = data.getProductList();
        List<String> expectedResult = products.stream().map(Product::getNomination).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/nomination"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(Product::getBrand).collect(Collectors.toSet())
                .stream().limit(10).toList();

        when(productService.getStringsByParameter(anyString())).thenReturn(expectedResult);

        result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "goods/search/brand"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        actualStatus = result.getResponse().getStatus();
        content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetUncheckedOrdersCountWithUser() throws Exception {
        int expectedStatus = 200;
        Integer expectedResult = 5;

        when(orderService.getUncheckedCount()).thenReturn(5);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/unchecked-count"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Integer actualResult = data.mapFromJson(content, Integer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetUncheckedOrdersCountWithStaff() throws Exception {
        int expectedStatus = 200;
        Integer expectedResult = 5;

        when(orderService.getUncheckedCount()).thenReturn(5);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/unchecked-count"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Integer actualResult = data.mapFromJson(content, Integer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetUncheckedOrdersCountWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Integer expectedResult = 5;

        when(orderService.getUncheckedCount()).thenReturn(5);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/unchecked-count"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Integer actualResult = data.mapFromJson(content, Integer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetUncheckedOrdersCountWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Integer expectedResult = 5;

        when(orderService.getUncheckedCount()).thenReturn(5);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "orders/unchecked-count"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Integer actualResult = data.mapFromJson(content, Integer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetWorkPriceWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("250");

        mockTxtReader.when(() -> TXTReader.getParameter("workPrice")).thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetWorkPriceWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("250");

        mockTxtReader.when(() -> TXTReader.getParameter("workPrice")).thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetWorkPriceWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("250");

        mockTxtReader.when(() -> TXTReader.getParameter("workPrice")).thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetWorkPriceWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("250");

        mockTxtReader.when(() -> TXTReader.getParameter("workPrice")).thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testSetWorkPrice() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("300");

        mockTxtReader.when(() -> TXTReader.setParameter("workPrice", expectedResult.get(0)))
                .thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price/new/" + expectedResult.get(0)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testSetWorkPriceWithUser() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("300");

        mockTxtReader.when(() -> TXTReader.setParameter("workPrice", expectedResult.get(0)))
                .thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price/new/" + expectedResult.get(0)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testSetWorkPriceWithStaff() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("300");

        mockTxtReader.when(() -> TXTReader.setParameter("workPrice", expectedResult.get(0)))
                .thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price/new/" + expectedResult.get(0)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testSetWorkPriceWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("300");

        mockTxtReader.when(() -> TXTReader.setParameter("workPrice", expectedResult.get(0)))
                .thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price/new/" + expectedResult.get(0)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testSetWorkPriceAllRoles() throws Exception {
        int expectedStatus = 200;
        List<String> expectedResult = List.of("300");

        mockTxtReader.when(() -> TXTReader.setParameter("workPrice", expectedResult.get(0)))
                .thenReturn(expectedResult.get(0));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(URL + "work-price/new/" + expectedResult.get(0)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetAuthUserId() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = List.of(customer.getId());

        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "user-id"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetAuthUserIdWithStaff() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = List.of(customer.getId());

        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "user-id"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetAuthUserIdWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = List.of(customer.getId());

        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "user-id"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetAuthUserIdWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<String> expectedResult = List.of(customer.getId());

        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(URL + "user-id"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<String> actualResult = data.mapFromJson(content, new TypeReference<List<String>>() {});

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }
}