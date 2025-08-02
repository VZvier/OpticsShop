package com.vzv.shop.product;

import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.web.ProductMvcController;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.service.PictureService;
import com.vzv.shop.service.ProductService;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.checkerframework.common.reflection.qual.MethodValBottom;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Import(ApplicationConfig.class)
@WebMvcTest(ProductMvcController.class)
class ProductMvcControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private PictureService picService;

    @MockBean
    private ProductService productService;

    private final DataForTests data = new DataForTests();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithUser
    void testGetAllWithUser() throws Exception {
        List<ProductDTO> products = data.getProductDTOList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/page"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/main-page"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetAllWithStaff() throws Exception {
        List<ProductDTO> products = data.getProductDTOList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/page"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/main-page"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetAllWithSysadmin() throws Exception {
        List<ProductDTO> products = data.getProductDTOList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/page"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/main-page"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetAllWithAllRoles() throws Exception {
        List<ProductDTO> products = data.getProductDTOList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/page"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/main-page"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetGoodsDetailsByProductIdWithUser() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/details/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/product-page"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetGoodsDetailsByProductIdWithStaff() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/details/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/product-page"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetGoodsDetailsByProductIdWithSysadmin() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/details/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/product-page"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetGoodsDetailsByProductIdWithAllRoles() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/details/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/product-page"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetProductCreationPage() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/new"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetProductCreationPageWithMockStaff() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/product-creation"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetProductCreationPageWithMockSysadmin() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/product-creation"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetProductCreationPageWithMockAll() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/product-creation"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetUpdatePage() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/renew/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetUpdatePageWithMockStaff() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/renew/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/product-renovation"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetUpdatePageWithSysadmin() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/renew/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/product-renovation"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetUpdatePageWithAllRoles() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.getDtoById(product.getId())).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products/renew/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/product-renovation"))
                .andReturn();
    }

    @Test
    @WithUser
    void testRemoveProductWithUser() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.deleteProduct(product.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testRemoveProductWithStaff() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.deleteProduct(product.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/shop/products/all"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testRemoveProductWithSysadmin() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.deleteProduct(product.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/shop/products/all"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testRemoveProductWithAllRoles() throws Exception {
        ProductDTO product = data.getProductDTOList().get(0);

        when(productService.deleteProduct(product.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/shop/products/rm/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/shop/products/all"))
                .andReturn();
    }
}