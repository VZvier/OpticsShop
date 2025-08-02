package com.vzv.shop.authentication;

import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.web.AuthMvcController;
import com.vzv.shop.entity.user.FullUser;
import com.vzv.shop.request.LoginRequest;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AuthMvcController.class)
@Import(ApplicationConfig.class)
class AuthMvcControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithAnonymousUser
    void testOpenLoginPageWithAnonymous() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("loginRequest"))
                .andExpect(MockMvcResultMatchers.model().attribute("loginRequest", new LoginRequest()))
                .andReturn();
    }

    @Test
    @WithUser
    void testOpenLoginPageWithUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/login"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testOpenLoginPageWithMockStaff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/login"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testOpenLoginPageWithMockSysadmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/login"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithMockAll
    void testOpenLoginPageWithMockAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/login"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testOpenRegistrationPageWithAnonymous() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/registration"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("registration"))
                .andExpect(MockMvcResultMatchers.model().attribute("registration", new FullUser("")))
                .andReturn();
    }

    @Test
    @WithUser
    void testOpenRegistrationPageWithUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/registration"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testOpenRegistrationPageWithStaff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/registration"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }
    @Test
    @WithMockSysadmin
    void testOpenRegistrationPageWithSysadmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/registration"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }
    @Test
    @WithMockAll
    void testOpenRegistrationPageWithAllRoles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/api/users/registration"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn();
    }
}