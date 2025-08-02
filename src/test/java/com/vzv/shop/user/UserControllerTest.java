package com.vzv.shop.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.rest.UserController;
import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.FullUser;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.service.UserService;
import com.vzv.shop.service.implementation.FullUserServiceImpl;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(UserController.class)
@Import(ApplicationConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @MockBean
    private UserService userService;

    @MockBean
    private FullUserServiceImpl fullUserService;

    MockedStatic<AuxiliaryUtils> mockAuxUtils;


    private final String URL = "/shop/api/users/";

    private final DataForTests data = new DataForTests();
    private final PasswordEncoder bCrypt = new BCryptPasswordEncoder();


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockAuxUtils = mockStatic(AuxiliaryUtils.class);

    }

    @AfterEach
    void tearDown() {
        if (mockAuxUtils != null) {
            mockAuxUtils.close();
        }
    }

    @Test
    @WithUser
    void testGetAuthUsersAddressWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User user = customer.getUser();
        int expectedStatus = 200;
        String expectedResult = customer.getAddress().toInlineOrEmpty();

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "address"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetAuthUsersAddressWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User user = customer.getUser();
        int expectedStatus = 200;
        String expectedResult = customer.getAddress().toInlineOrEmpty();

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "address"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetAuthUsersAddressWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User user = customer.getUser();
        int expectedStatus = 200;
        String expectedResult = customer.getAddress().toInlineOrEmpty();

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "address"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetAuthUsersAddressWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User user = customer.getUser();
        int expectedStatus = 200;
        String expectedResult = customer.getAddress().toInlineOrEmpty();

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "address"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetAuthUserFullInfoWithUser() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-customer/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetAuthUserFullInfoWithStaff() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-customer/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetAuthUserFullInfoWithSysadmin() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-customer/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetAuthUserFullInfoWithAllRoles() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-customer/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetAuthUserUserInfoWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-user/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetAuthUserUserInfoWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-user/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetAuthUserUserInfoWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-user/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetAuthUserUserInfoWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getByLogin(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "auth-user/info"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetUserByIdWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetUserByIdWithWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetUserByIdWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetUserByIdWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        User expectedResult = customer.getUser();
        int expectedStatus = 200;

        when(userService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        User actualResult = data.mapFromJson(content, User.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetCustomerByIdWithUser() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.getCustomerById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "customer/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetCustomerByIdWithStaff() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.getCustomerById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "customer/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetCustomerByIdWithSysadmin() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.getCustomerById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "customer/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetCustomerByIdWithAllRoles() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.getCustomerById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "customer/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testFindCustomersWithUser() throws Exception {
        List<Customer> expectedResult = data.getCustomerList();
        Customer customer = expectedResult.get(0);
        int expectedStatus = 200;
        String fullName = customer.getLName() + " " + customer.getFName() + " " + customer.getFatherName();


        when(userService.findCustomerByCriteria(fullName)).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "search/" + fullName))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Customer> actualResult = data.mapFromJson(content, new TypeReference<List<Customer>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testFindCustomersWithStaff() throws Exception {
        List<Customer> expectedResult = data.getCustomerList();
        Customer customer = expectedResult.get(0);
        int expectedStatus = 200;
        String fullName = customer.getLName() + " " + customer.getFName() + " " + customer.getFatherName();


        when(userService.findCustomerByCriteria(fullName)).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "search/" + fullName))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Customer> actualResult = data.mapFromJson(content, new TypeReference<List<Customer>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testFindCustomersWithSysadmin() throws Exception {
        List<Customer> expectedResult = data.getCustomerList();
        Customer customer = expectedResult.get(0);
        int expectedStatus = 200;
        String fullName = customer.getLName() + " " + customer.getFName() + " " + customer.getFatherName();


        when(userService.findCustomerByCriteria(fullName)).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "search/" + fullName))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Customer> actualResult = data.mapFromJson(content, new TypeReference<List<Customer>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testFindCustomersWithAllRoles() throws Exception {
        List<Customer> expectedResult = data.getCustomerList();
        Customer customer = expectedResult.get(0);
        int expectedStatus = 200;
        String fullName = customer.getLName() + " " + customer.getFName() + " " + customer.getFatherName();


        when(userService.findCustomerByCriteria(fullName)).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "search/" + fullName))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Customer> actualResult = data.mapFromJson(content, new TypeReference<List<Customer>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testGetFullUserByIdWithUser() throws Exception {
        FullUser expectedResult = data.getFullUserList().get(0);
        int expectedStatus = 200;

        when(fullUserService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "full/by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FullUser actualResult = data.mapFromJson(content, FullUser.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetFullUserByIdWithStaff() throws Exception {
        FullUser expectedResult = data.getFullUserList().get(0);
        int expectedStatus = 200;

        when(fullUserService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "full/by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FullUser actualResult = data.mapFromJson(content, FullUser.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetFullUserByIdWithSysadmin() throws Exception {
        FullUser expectedResult = data.getFullUserList().get(0);
        int expectedStatus = 200;

        when(fullUserService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "full/by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FullUser actualResult = data.mapFromJson(content, FullUser.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetFullUserByIdWithAllRoles() throws Exception {
        FullUser expectedResult = data.getFullUserList().get(0);
        int expectedStatus = 200;

        when(fullUserService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URL + "full/by-id/" + expectedResult.getId()))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FullUser actualResult = data.mapFromJson(content, FullUser.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testUpdateProfileWithUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> expectedResult = new HashMap<>();
        Customer customer = data.getCustomerList().get(0);
        Customer updated = new Customer();
        BeanUtils.copyProperties(customer, updated);
        updated.setFName("Олег");
        updated.setLName("Олегов");
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "renew/customer");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);
        expectedResult.put("welcomeMessage", "Ваш профиль обновлён!");
        expectedResult.put("userInfo", updated);
        String mapAsString = mapper.writeValueAsString(expectedResult);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(updated);
        when(userService.saveCustomer(any())).thenReturn(updated);

        MvcResult mvcResult = mvc.perform(mockReq).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(mapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testUpdateProfileWithStaff() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> expectedResult = new HashMap<>();
        Customer customer = data.getCustomerList().get(0);
        Customer updated = new Customer();
        BeanUtils.copyProperties(customer, updated);
        updated.setFName("Олег");
        updated.setLName("Олегов");
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "renew/customer");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);
        expectedResult.put("welcomeMessage", "Ваш профиль обновлён!");
        expectedResult.put("userInfo", updated);
        String mapAsString = mapper.writeValueAsString(expectedResult);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(updated);
        when(userService.saveCustomer(any())).thenReturn(updated);

        MvcResult mvcResult = mvc.perform(mockReq).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(mapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testUpdateProfileWithSysadmin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> expectedResult = new HashMap<>();
        Customer customer = data.getCustomerList().get(0);
        Customer updated = new Customer();
        BeanUtils.copyProperties(customer, updated);
        updated.setFName("Олег");
        updated.setLName("Олегов");
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "renew/customer");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);
        expectedResult.put("welcomeMessage", "Ваш профиль обновлён!");
        expectedResult.put("userInfo", updated);
        String mapAsString = mapper.writeValueAsString(expectedResult);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(updated);
        when(userService.saveCustomer(any())).thenReturn(updated);

        MvcResult mvcResult = mvc.perform(mockReq).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(mapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testUpdateProfileWithAllRoles() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> expectedResult = new HashMap<>();
        Customer customer = data.getCustomerList().get(0);
        Customer updated = new Customer();
        BeanUtils.copyProperties(customer, updated);
        updated.setFName("Олег");
        updated.setLName("Олегов");
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "renew/customer");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);
        expectedResult.put("welcomeMessage", "Ваш профиль обновлён!");
        expectedResult.put("userInfo", updated);
        String mapAsString = mapper.writeValueAsString(expectedResult);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(updated);
        when(userService.saveCustomer(any())).thenReturn(updated);

        MvcResult mvcResult = mvc.perform(mockReq).andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(mapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testCreateUserWithUser() throws Exception {
        int expectedStatus = 403;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "new");

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithMockStaff
    void testCreateUserWithStaff() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = data.getCustomerList().get(0);
        Map<String, Object> expectedResult = new HashMap<>();
        int expectedStatus = 200;
        String autoGenPass = "testPassword";

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "new");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        expectedResult.put("customer", customer);
        expectedResult.put("pass", "Сгенерированный пароль: '" + autoGenPass + "'");
        expectedResult.put("message", "Customer " + customer + " saved successfully!");
        String expectedMapAsString = mapper.writeValueAsString(expectedResult);


        when(userService.generateId()).thenReturn(customer.getId());
        when(userService.reformatPhone(anyString())).thenReturn(customer.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(customer.getContact().getEmail());
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mockAuxUtils.when(() -> AuxiliaryUtils.makeRandomString(12)).thenReturn(autoGenPass);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(expectedMapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testCreateUserWithSysadmin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = data.getCustomerList().get(0);
        Map<String, Object> expectedResult = new HashMap<>();
        int expectedStatus = 200;
        String autoGenPass = "testPassword";

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "new");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        expectedResult.put("customer", customer);
        expectedResult.put("pass", "Сгенерированный пароль: '" + autoGenPass + "'");
        expectedResult.put("message", "Customer " + customer + " saved successfully!");
        String expectedMapAsString = mapper.writeValueAsString(expectedResult);


        when(userService.generateId()).thenReturn(customer.getId());
        when(userService.reformatPhone(anyString())).thenReturn(customer.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(customer.getContact().getEmail());
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mockAuxUtils.when(() -> AuxiliaryUtils.makeRandomString(12)).thenReturn(autoGenPass);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(expectedMapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    @WithMockAll
    void testCreateUserWithAllRoles() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = data.getCustomerList().get(0);
        Map<String, Object> expectedResult = new HashMap<>();
        int expectedStatus = 200;
        String autoGenPass = "testPassword";

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "new");

        for (String name : Arrays.stream(customer.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = customer.getClass()
                    .getMethod(data.isMethodExists(
                            customer, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(customer);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        expectedResult.put("customer", customer);
        expectedResult.put("pass", "Сгенерированный пароль: '" + autoGenPass + "'");
        expectedResult.put("message", "Customer " + customer + " saved successfully!");
        String expectedMapAsString = mapper.writeValueAsString(expectedResult);


        when(userService.generateId()).thenReturn(customer.getId());
        when(userService.reformatPhone(anyString())).thenReturn(customer.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(customer.getContact().getEmail());
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.makeRandomString(12)).thenReturn(autoGenPass);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualResult = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {
        });
        expectedResult = data.mapFromJson(expectedMapAsString, new TypeReference<HashMap<String, Object>>() {
        });

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testAddUserToCustomerWithUser() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "customer/add-new");

        for (String name : Arrays.stream(expectedResult.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = expectedResult.getClass()
                    .getMethod(data.isMethodExists(
                            expectedResult, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(expectedResult);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.save(any())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testAddUserToCustomerWithStaff() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "customer/add-new");

        for (String name : Arrays.stream(expectedResult.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = expectedResult.getClass()
                    .getMethod(data.isMethodExists(
                            expectedResult, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(expectedResult);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.save(any())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testAddUserToCustomerWithSysadmin() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "customer/add-new");

        for (String name : Arrays.stream(expectedResult.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = expectedResult.getClass()
                    .getMethod(data.isMethodExists(
                            expectedResult, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(expectedResult);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.save(any())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testAddUserToCustomerWithAllRoles() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        User user = expectedResult.getUser();
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "customer/add-new");

        for (String name : Arrays.stream(expectedResult.getClass().getDeclaredFields()).map(Field::getName).toList()) {
            String capitalized = StringUtils.capitalize(name);
            Object value = expectedResult.getClass()
                    .getMethod(data.isMethodExists(
                            expectedResult, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                    ).invoke(expectedResult);
            if (value != null) {
                mockReq.param(name, String.valueOf(value));
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.save(any())).thenReturn(user);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testUpdateCustomerWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer expectedResult = new Customer();
        BeanUtils.copyProperties(customer, expectedResult);
        expectedResult.setFName("Oleg");
        expectedResult.setLName("Olegov");
        expectedResult.setFatherName("Olegovich");
        expectedResult.setAddress(data.getAddressList().get(2));
        expectedResult.setContact(data.getContactList().get(4));
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "renew/customer");

        List<Object> objects = List.of(expectedResult, expectedResult.getUser(),
                expectedResult.getAddress(), expectedResult.getContact());

        for (Object obj : objects) {
            for (String name : Arrays.stream(obj.getClass().getDeclaredFields()).map(Field::getName).toList()) {
                String capitalized = StringUtils.capitalize(name);
                Object value = obj.getClass()
                        .getMethod(data.isMethodExists(
                                obj, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                        ).invoke(obj);
                if (value != null) {
                    mockReq.param(name, String.valueOf(value));
                }
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testUpdateCustomerWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer expectedResult = new Customer();
        BeanUtils.copyProperties(customer, expectedResult);
        expectedResult.setFName("Oleg");
        expectedResult.setLName("Olegov");
        expectedResult.setFatherName("Olegovich");
        expectedResult.setAddress(data.getAddressList().get(2));
        expectedResult.setContact(data.getContactList().get(4));
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "renew/customer");

        List<Object> objects = List.of(expectedResult, expectedResult.getUser(),
                expectedResult.getAddress(), expectedResult.getContact());

        for (Object obj : objects) {
            for (String name : Arrays.stream(obj.getClass().getDeclaredFields()).map(Field::getName).toList()) {
                String capitalized = StringUtils.capitalize(name);
                Object value = obj.getClass()
                        .getMethod(data.isMethodExists(
                                obj, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                        ).invoke(obj);
                if (value != null) {
                    mockReq.param(name, String.valueOf(value));
                }
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testUpdateCustomerWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer expectedResult = new Customer();
        BeanUtils.copyProperties(customer, expectedResult);
        expectedResult.setFName("Oleg");
        expectedResult.setLName("Olegov");
        expectedResult.setFatherName("Olegovich");
        expectedResult.setAddress(data.getAddressList().get(2));
        expectedResult.setContact(data.getContactList().get(4));
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "renew/customer");

        List<Object> objects = List.of(expectedResult, expectedResult.getUser(),
                expectedResult.getAddress(), expectedResult.getContact());

        for (Object obj : objects) {
            for (String name : Arrays.stream(obj.getClass().getDeclaredFields()).map(Field::getName).toList()) {
                String capitalized = StringUtils.capitalize(name);
                Object value = obj.getClass()
                        .getMethod(data.isMethodExists(
                                obj, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                        ).invoke(obj);
                if (value != null) {
                    mockReq.param(name, String.valueOf(value));
                }
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testUpdateCustomerWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer expectedResult = new Customer();
        BeanUtils.copyProperties(customer, expectedResult);
        expectedResult.setFName("Oleg");
        expectedResult.setLName("Olegov");
        expectedResult.setFatherName("Olegovich");
        expectedResult.setAddress(data.getAddressList().get(2));
        expectedResult.setContact(data.getContactList().get(4));
        int expectedStatus = 200;

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.POST, URL + "renew/customer");

        List<Object> objects = List.of(expectedResult, expectedResult.getUser(),
                expectedResult.getAddress(), expectedResult.getContact());

        for (Object obj : objects) {
            for (String name : Arrays.stream(obj.getClass().getDeclaredFields()).map(Field::getName).toList()) {
                String capitalized = StringUtils.capitalize(name);
                Object value = obj.getClass()
                        .getMethod(data.isMethodExists(
                                obj, "get" + capitalized) ? "get" + capitalized : "is" + capitalized
                        ).invoke(obj);
                if (value != null) {
                    mockReq.param(name, String.valueOf(value));
                }
            }
        }
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testChangePasswordWithUser() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .request(HttpMethod.PUT, URL + "renew/customer/pass");
        mockReq.param("id", expectedResult.getId());

        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testChangePasswordWithStaff() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .request(HttpMethod.PUT, URL + "renew/customer/pass");
        mockReq.param("id", expectedResult.getId());

        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testChangePasswordWithSysadmin() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .request(HttpMethod.PUT, URL + "renew/customer/pass");
        mockReq.param("id", expectedResult.getId());

        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testChangePasswordWithAllRoles() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .request(HttpMethod.PUT, URL + "renew/customer/pass");
        mockReq.param("id", expectedResult.getId());

        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testUpdateFullNameWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer otherCustomer = data.getCustomerList().get(1);
        String expectedResult = data.getCustomerList().get(1).toStringFullName();
        int expectedStatus = 200;


        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "customer/renew-name");
        mockReq.param("id", customer.getId());
        mockReq.param("newLastName", otherCustomer.getLName());
        mockReq.param("newFirstName", otherCustomer.getFName());
        mockReq.param("newFatherName", otherCustomer.getFatherName());
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testUpdateFullNameWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer otherCustomer = data.getCustomerList().get(1);
        String expectedResult = data.getCustomerList().get(1).toStringFullName();
        int expectedStatus = 200;


        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "customer/renew-name");
        mockReq.param("id", customer.getId());
        mockReq.param("newLastName", otherCustomer.getLName());
        mockReq.param("newFirstName", otherCustomer.getFName());
        mockReq.param("newFatherName", otherCustomer.getFatherName());
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testUpdateFullNameWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer otherCustomer = data.getCustomerList().get(1);
        String expectedResult = data.getCustomerList().get(1).toStringFullName();
        int expectedStatus = 200;


        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "customer/renew-name");
        mockReq.param("id", customer.getId());
        mockReq.param("newLastName", otherCustomer.getLName());
        mockReq.param("newFirstName", otherCustomer.getFName());
        mockReq.param("newFatherName", otherCustomer.getFatherName());
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testUpdateFullNameWithAlRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Customer otherCustomer = data.getCustomerList().get(1);
        String expectedResult = data.getCustomerList().get(1).toStringFullName();
        int expectedStatus = 200;


        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "customer/renew-name");
        mockReq.param("id", customer.getId());
        mockReq.param("newLastName", otherCustomer.getLName());
        mockReq.param("newFirstName", otherCustomer.getFName());
        mockReq.param("newFatherName", otherCustomer.getFatherName());
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(any())).thenReturn(customer);
        when(userService.getCustomerById(anyString())).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(mockReq)
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String actualResult = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testUpdatePropertyWithUser() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.reformatPhone(anyString())).thenReturn(expectedResult.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(expectedResult.getContact().getEmail());
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.updateUser(any())).thenReturn(expectedResult.getUser());
        when(userService.saveContact(any())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put(URL + "customer/" + expectedResult.getId() + "/renew-prop/phone/+38(043)423-42-12"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testUpdatePropertyWithStaff() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.reformatPhone(anyString())).thenReturn(expectedResult.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(expectedResult.getContact().getEmail());
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.updateUser(any())).thenReturn(expectedResult.getUser());
        when(userService.saveContact(any())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put(URL + "customer/" + expectedResult.getId() + "/renew-prop/phone/+38(043)423-42-12"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testUpdatePropertyWithSysadmin() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.reformatPhone(anyString())).thenReturn(expectedResult.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(expectedResult.getContact().getEmail());
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.updateUser(any())).thenReturn(expectedResult.getUser());
        when(userService.saveContact(any())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put(URL + "customer/" + expectedResult.getId() + "/renew-prop/phone/+38(043)423-42-12"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testUpdatePropertyWithAllRoles() throws Exception {
        Customer expectedResult = data.getCustomerList().get(0);
        int expectedStatus = 200;

        when(userService.reformatPhone(anyString())).thenReturn(expectedResult.getContact().getPhone());
        when(userService.reformatEmail(anyString())).thenReturn(expectedResult.getContact().getEmail());
        when(userService.getCustomerById(anyString())).thenReturn(expectedResult);
        when(userService.saveCustomer(any())).thenReturn(expectedResult);
        when(userService.encodePass(anyString())).thenReturn(expectedResult.getUser().getPassword());
        when(userService.updateUser(any())).thenReturn(expectedResult.getUser());
        when(userService.saveContact(any())).thenReturn(expectedResult);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put(URL + "customer/" + expectedResult.getId() + "/renew-prop/phone/+38(043)423-42-12"))
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Customer actualResult = data.mapFromJson(content, Customer.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithUser
    void testDeleteUserWithUser() throws Exception {
        when(userService.delete(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/TestUserId"))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testDeleteUserWithStaff() throws Exception {
        when(userService.delete(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/TestUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testDeleteUserWithSysadmin() throws Exception {
        when(userService.delete(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/TestUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockAll
    void testDeleteUserWithAllRoles() throws Exception {
        when(userService.delete(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/TestUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithUser
    void testDeleteCustomerWithUser() throws Exception {
        when(userService.deleteCustomer(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/customer/TestCustomerId"))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testDeleteCustomerWithStaff() throws Exception {
        when(userService.deleteCustomer(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/customer/TestCustomerId"))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testDeleteCustomerWithSysadmin() throws Exception {
        when(userService.deleteCustomer(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/customer/TestCustomerId"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockAll
    void testDeleteCustomerWithAllRoles() throws Exception {
        when(userService.deleteCustomer(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete(URL + "rm/customer/TestCustomerId"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}