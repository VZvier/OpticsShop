package com.vzv.shop.product;

import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.rest.ProductController;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.product.*;
import com.vzv.shop.service.PictureService;
import com.vzv.shop.service.ProductService;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ProductController.class)
@Import(ApplicationConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    @MockBean
    private PictureService picService;

    private final DataForTests data = new DataForTests();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithUser
    void testGetProductByIdWithUser() throws Exception {
        int expectedStatus = 403;
        Product product = data.getProductList().get(0);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/" + product.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithMockStaff
    void testGetProductByIdWithStaff() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, expectedResult.getClass());

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetProductByIdWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, expectedResult.getClass());

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetProductByIdWithUserWithAll() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, expectedResult.getClass());

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetAllProductDtoWithUser() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockStaff
    void testGetAllProductDtoWithStaff() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockSysadmin
    void testGetAllProductDtoWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockAll
    void testGetAllProductDtoWithAll() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithUser
    void testGetByNomination() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(data.getProductDTOList().get(0).getNomination())).toList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all-by-nomination/"
                        + expectedResult.get(0).getNomination()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockStaff
    void testGetByNominationWithStaff() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(data.getProductDTOList().get(0).getNomination())).toList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all-by-nomination/"
                        + expectedResult.get(0).getNomination()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockSysadmin
    void testGetByNominationWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(data.getProductDTOList().get(0).getNomination())).toList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all-by-nomination/"
                        + expectedResult.get(0).getNomination()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockAll
    void testGetByNominationWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(data.getProductDTOList().get(0).getNomination())).toList();

        when(productService.getAll()).thenReturn(expectedResult);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/all-by-nomination/"
                        + expectedResult.get(0).getNomination()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithUser
    void testGetByNominationAndBrandWithUser() throws Exception {
        int expectedStatus = 200;
        ProductDTO product = data.getProductDTOList().get(0);
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(product.getNomination())
                        && p.getBrand().equals(product.getBrand())).toList();

        when(productService.getAll()).thenReturn(data.getProductDTOList());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/by-nomination-and-brand/"
                        + product.getNomination() + "/" + product.getBrand()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockStaff
    void testGetByNominationAndBrandWithStaff() throws Exception {
        int expectedStatus = 200;
        ProductDTO product = data.getProductDTOList().get(0);
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(product.getNomination())
                        && p.getBrand().equals(product.getBrand())).toList();

        when(productService.getAll()).thenReturn(data.getProductDTOList());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/by-nomination-and-brand/"
                        + product.getNomination() + "/" + product.getBrand()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockSysadmin
    void testGetByNominationAndBrandWithSysadmin() throws Exception {
        int expectedStatus = 200;
        ProductDTO product = data.getProductDTOList().get(0);
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(product.getNomination())
                        && p.getBrand().equals(product.getBrand())).toList();

        when(productService.getAll()).thenReturn(data.getProductDTOList());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/by-nomination-and-brand/"
                        + product.getNomination() + "/" + product.getBrand()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithMockAll
    void testGetByNominationAndBrandWithAllRoles() throws Exception {
        int expectedStatus = 200;
        ProductDTO product = data.getProductDTOList().get(0);
        List<ProductDTO> expectedResult = data.getProductDTOList().stream()
                .filter(p -> p.getNomination().equals(product.getNomination())
                        && p.getBrand().equals(product.getBrand())).toList();

        when(productService.getAll()).thenReturn(data.getProductDTOList());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/by-nomination-and-brand/"
                        + product.getNomination() + "/" + product.getBrand()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ProductDTO[] actualResult = data.mapFromJson(content, ProductDTO[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, List.of(actualResult));
    }

    @Test
    @WithUser
    void testGetEmptyFormWithUser() throws Exception {
        int expectedStatus = 403;

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/frame"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/lens"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/medical-equipment"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);


        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/liquid"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/other"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithMockStaff
    void testGetEmptyFormWithStaff() throws Exception {
        int expectedStatus = 200;
        String id = "ProductTestId";
        Product product = data.getProductList().get(0);
        Product expectedResult = new Frame();

        when(productService.getProductForm("frame", null)).thenReturn(new Frame());
        when(productService.getProductForm("lens", null)).thenReturn(new Lens());
        when(productService.getProductForm("medical-equipment", null)).thenReturn(new MedicalEquipment());
        when(productService.getProductForm("liquid", null)).thenReturn(new Liquid());
        when(productService.getProductForm("other", null)).thenReturn(new Other());
        when(productService.makeId("product")).thenReturn(product.getId());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/frame"))
                .andReturn();

        expectedResult.setId(product.getId());
        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, Frame.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Lens();
        expectedResult.setId(product.getId());

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/lens"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Lens.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new MedicalEquipment();
        expectedResult.setId(product.getId());

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/medical-equipment"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, MedicalEquipment.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Liquid();
        expectedResult.setId(product.getId());

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/liquid"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Liquid.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Other();
        expectedResult.setId(product.getId());

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/other"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Other.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetEmptyFormWithSysadmin() throws Exception {
        int expectedStatus = 200;
        String id = "ProductTestId";
        Product expectedResult = new Frame();

        when(productService.getProductForm("frame", null)).thenReturn(new Frame());
        when(productService.getProductForm("lens", null)).thenReturn(new Lens());
        when(productService.getProductForm("medical-equipment", null)).thenReturn(new MedicalEquipment());
        when(productService.getProductForm("liquid", null)).thenReturn(new Liquid());
        when(productService.getProductForm("other", null)).thenReturn(new Other());
        when(productService.makeId("product")).thenReturn(id);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/frame"))
                .andReturn();

        expectedResult.setId(id);
        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, Frame.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Lens();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/lens"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Lens.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new MedicalEquipment();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/medical-equipment"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, MedicalEquipment.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Liquid();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/liquid"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Liquid.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Other();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/other"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Other.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetEmptyFormWithAllRoles() throws Exception {
        int expectedStatus = 200;
        String id = "ProductTestId";
        Product expectedResult = new Frame();

        when(productService.getProductForm("frame", null)).thenReturn(new Frame());
        when(productService.getProductForm("lens", null)).thenReturn(new Lens());
        when(productService.getProductForm("medical-equipment", null)).thenReturn(new MedicalEquipment());
        when(productService.getProductForm("liquid", null)).thenReturn(new Liquid());
        when(productService.getProductForm("other", null)).thenReturn(new Other());
        when(productService.makeId("product")).thenReturn(id);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/frame"))
                .andReturn();

        expectedResult.setId(id);
        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, Frame.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Lens();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/lens"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Lens.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new MedicalEquipment();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/medical-equipment"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, MedicalEquipment.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Liquid();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/liquid"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Liquid.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

        expectedResult = new Other();
        expectedResult.setId(id);

        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/products/forms/other"))
                .andReturn();

        actualStatus = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        actualResult = data.mapFromJson(content, Other.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testAddNewProductWithUser() throws Exception {
        int expectedStatus = 403;

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/products/new/");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = this.mockMvc.perform(mockRequest).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithMockStaff
    void testAddNewProductWithStaff() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId("product")).thenReturn(expectedResult.getId());
        when(productService.save(any())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/products/new/");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
        ).toList();

        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                            (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }
        MvcResult mvcResult = this.mockMvc.perform(mockRequest.with(csrf())).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, expectedResult.getClass());

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testAddNewProductWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId("product")).thenReturn(expectedResult.getId());
        when(productService.save(any())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/products/new/");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
        ).toList();

        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                            (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }

        MvcResult mvcResult = this.mockMvc.perform(mockRequest.with(csrf())).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, expectedResult.getClass());

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testAddNewProductWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId("product")).thenReturn(expectedResult.getId());
        when(productService.save(any())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/products/new/");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
        ).toList();

        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                            (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }

        MvcResult mvcResult = this.mockMvc.perform(mockRequest.with(csrf())).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Product actualResult = data.mapFromJson(content, expectedResult.getClass());

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testUpdateProductWithUser() throws Exception {
        int expectedStatus = 403;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId(anyString())).thenReturn(expectedResult.getId());
        when(picService.getPicturesByIds(anyList())).thenReturn(new ArrayList<>());
        when(productService.isImgExistOrNew(any())).thenReturn(new Picture());
        when(productService.updateProduct(any(Product.class))).thenReturn(expectedResult);
        when(productService.getById(anyString())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/products/renew");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                    Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                    Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
                ).toList();
        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                    (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }

        MvcResult mvcResult = this.mockMvc.perform(mockRequest).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithMockStaff
    void testUpdateProductWithStaff() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId(anyString())).thenReturn(expectedResult.getId());
        when(picService.getPicturesByIds(anyList())).thenReturn(new ArrayList<>());
        when(productService.isImgExistOrNew(any())).thenReturn(new Picture());
        when(productService.updateProduct(any(Product.class))).thenReturn(expectedResult);
        when(productService.getById(anyString())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/products/renew");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
        ).toList();
        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                            (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }

        MvcResult mvcResult = this.mockMvc.perform(mockRequest.with(csrf())).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        Product actualResult = data.mapFromJson(content, Frame.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testUpdateProductWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId(anyString())).thenReturn(expectedResult.getId());
        when(picService.getPicturesByIds(anyList())).thenReturn(new ArrayList<>());
        when(productService.isImgExistOrNew(any())).thenReturn(new Picture());
        when(productService.updateProduct(any(Product.class))).thenReturn(expectedResult);
        when(productService.getById(anyString())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/products/renew");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
        ).toList();
        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                            (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }

        MvcResult mvcResult = this.mockMvc.perform(mockRequest.with(csrf())).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        Product actualResult = data.mapFromJson(content, Frame.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testUpdateProductWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Product expectedResult = data.getProductList().get(0);

        when(productService.getProductForm(anyString(), any())).thenReturn(expectedResult);
        when(productService.makeId(anyString())).thenReturn(expectedResult.getId());
        when(picService.getPicturesByIds(anyList())).thenReturn(new ArrayList<>());
        when(productService.isImgExistOrNew(any())).thenReturn(new Picture());
        when(productService.updateProduct(any(Product.class))).thenReturn(expectedResult);
        when(productService.getById(anyString())).thenReturn(expectedResult);

        MockMultipartHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/products/renew");
        mockRequest.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Field> fields = Stream.concat(
                Arrays.stream(expectedResult.getClass().getDeclaredFields()),
                Arrays.stream(expectedResult.getClass().getSuperclass().getDeclaredFields())
        ).toList();
        for (Field field : fields){
            String name = StringUtils.capitalize(field.getName());
            Object value = expectedResult.getClass().getMethod(
                            (data.isMethodExists(expectedResult, "get" + name) ? "get" + name : "is" + name))
                    .invoke(expectedResult);
            if(!field.getName().equals("id")) {
                mockRequest.param(field.getName(), String.valueOf(value));
            }
        }

        MvcResult mvcResult = this.mockMvc.perform(mockRequest.with(csrf())).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(content);
        Product actualResult = data.mapFromJson(content, Frame.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testUpdateGoodsPricesWithUser() throws Exception {
        MockMultipartHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, "/shop/products/price/renew/percent");
        mockRequest
                .param("action", "increase")
                .param("criteria", "Mien")
                .param("percent", "50")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        doNothing().when(productService).changePricesByCriteria(anyString(), anyString(), anyString(), anyInt());

        this.mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testUpdateGoodsPricesWithStaff() throws Exception {
        MockMultipartHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, "/shop/products/price/renew/percent");
        mockRequest
                .param("action", "increase")
                .param("criteria", "Mien")
                .param("percent", "50")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        doNothing().when(productService).changePricesByCriteria(anyString(), anyString(), anyString(), anyInt());

        this.mockMvc.perform(mockRequest.with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testUpdateGoodsPricesWithSysadmin() throws Exception {
        MockMultipartHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, "/shop/products/price/renew/percent");
        mockRequest
                .param("action", "increase")
                .param("criteria", "Mien")
                .param("percent", "50")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        doNothing().when(productService).changePricesByCriteria(anyString(), anyString(), anyString(), anyInt());

        this.mockMvc.perform(mockRequest.with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockAll
    void testUpdateGoodsPricesWithAllRoles() throws Exception {
        MockMultipartHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, "/shop/products/price/renew/percent");
        mockRequest
                .param("action", "increase")
                .param("criteria", "Mien")
                .param("percent", "50")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        doNothing().when(productService).changePricesByCriteria(anyString(), anyString(), anyString(), anyInt());

        this.mockMvc.perform(mockRequest.with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithUser
    void testClearProductsTableWithUser() throws Exception {
        when(productService.deleteAll()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm-all"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testClearProductsTableWithStaff() throws Exception {
        when(productService.deleteAll()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm-all"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testClearProductsTableWithSysadmin() throws Exception {
        when(productService.deleteAll()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm-all").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockAll
    void testClearProductsTableWithAllRoles() throws Exception {
        when(productService.deleteAll()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm-all").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}