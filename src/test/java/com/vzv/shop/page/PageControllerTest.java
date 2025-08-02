package com.vzv.shop.page;

import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.web.PageController;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@WebMvcTest(PageController.class)
@Import(ApplicationConfig.class)
class PageControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithUser
    void testGetIndexPageWithUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetIndexPageWithStaff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetIndexPageWithMockSysadmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetIndexPageWithMockAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetAdminPageWithUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/administration"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetAdminPageWithStaff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/administration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/users"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetAdminPageWithMockSysadmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/administration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/users"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetAdminPageWithMockAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/administration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/users"))
                .andReturn();
    }
}