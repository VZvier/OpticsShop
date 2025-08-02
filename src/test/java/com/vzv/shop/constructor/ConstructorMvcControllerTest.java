package com.vzv.shop.constructor;

import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.web.ConstructorMvcController;
import com.vzv.shop.dto.ProductDTO;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(ConstructorMvcController.class)
@Import(ApplicationConfig.class)
class ConstructorMvcControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private final DataForTests data = new DataForTests();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }


    @Test
    @WithUser
    void testGetConstructorWithUser() throws Exception {
        List<ProductDTO> products = data.getProductDTOList().stream()
                .filter(p -> List.of("lens", "frame").contains(p.getNomination())).toList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/api/constructor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/glasses-constructor"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetConstructorWithStaff() throws Exception {
        List<ProductDTO> products = data.getProductDTOList().stream()
                .filter(p -> List.of("lens", "frame").contains(p.getNomination())).toList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/api/constructor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/glasses-constructor"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetConstructorWithSysadmin() throws Exception {
        List<ProductDTO> products = data.getProductDTOList().stream()
                .filter(p -> List.of("lens", "frame").contains(p.getNomination())).toList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/api/constructor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/glasses-constructor"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetConstructorWithAllRoles() throws Exception {
        List<ProductDTO> products = data.getProductDTOList().stream()
                .filter(p -> List.of("lens", "frame").contains(p.getNomination())).toList();

        when(productService.getAll()).thenReturn(products);

        mvc.perform(MockMvcRequestBuilders.get("/shop/api/constructor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", products))
                .andExpect(MockMvcResultMatchers.view().name("pages/glasses-constructor"))
                .andReturn();
    }
}