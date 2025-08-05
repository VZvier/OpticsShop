package com.vzv.shop.user;

import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.web.UserMvcController;
import com.vzv.shop.entity.user.Address;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.service.UserService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(UserMvcController.class)
@Import(ApplicationConfig.class)
class UserMvcControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final DataForTests data = new DataForTests();
    private final String URL = "/shop/api/users/";

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithUser
    void testGetUserInfoPageWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "page/" + customer.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetUserInfoPageWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "page/" + customer.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetUserInfoPageWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "page/" + customer.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetUserInfoPageWithAll() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "page/" + customer.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetCreateUserPageWithUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL + "new"))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetCreateUserPageWithStaff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL + "new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/create-user"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetCreateUserPageWithSysadmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL + "new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/create-user"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetCreateUserPageWithAllRoles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL + "new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/create-user"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetAllWithUser() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(userService.getAllCustomers()).thenReturn(customers);

        mvc.perform(MockMvcRequestBuilders.get(URL + "customers/all").with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetAllWithStaff() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(userService.getAllCustomers()).thenReturn(customers);

        mvc.perform(MockMvcRequestBuilders.get(URL + "customers/all").with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerList"))
                .andExpect(MockMvcResultMatchers.model().attribute("customerList", customers))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/users"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetAllWithSysadmin() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(userService.getAllCustomers()).thenReturn(customers);

        mvc.perform(MockMvcRequestBuilders.get(URL + "customers/all").with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerList"))
                .andExpect(MockMvcResultMatchers.model().attribute("customerList", customers))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/users"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetAllWithAllRoles() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(userService.getAllCustomers()).thenReturn(customers);

        mvc.perform(MockMvcRequestBuilders.get(URL + "customers/all").with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerList"))
                .andExpect(MockMvcResultMatchers.model().attribute("customerList", customers))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/users"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetAddressPageWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        mvc.perform(MockMvcRequestBuilders.get(URL + "address/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/delivery-address"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetAddressPageWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        mvc.perform(MockMvcRequestBuilders.get(URL + "address/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/delivery-address"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetAddressPageWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        mvc.perform(MockMvcRequestBuilders.get(URL + "address/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/delivery-address"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetAddressPageWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.getByLogin(anyString())).thenReturn(customer.getUser());

        mvc.perform(MockMvcRequestBuilders.get(URL + "address/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/delivery-address"))
                .andReturn();
    }

    @Test
    @WithUser
    void testOpenUpdatePageWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "renew/customer"))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testOpenUpdatePageWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "renew/customer/" + customer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testOpenUpdatePageWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);

        mvc.perform(MockMvcRequestBuilders.get(URL + "renew/customer/" + customer.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testOpenUpdatePageWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        mvc.perform(MockMvcRequestBuilders.get(URL + "renew/customer/" + customer.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithUser
    void testSaveUpdateAddressWithUser() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Address address = data.getAddressList().get(1);

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "address/renew/TestUserId1");
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        for (Field field : address.getClass().getDeclaredFields()) {
            String name = StringUtils.capitalize(field.getName());
            String fieldVal = (String) address.getClass().getMethod(data.isMethodExists(address, "get" + name)
                    ? "get" + name : "is" + name).invoke(address);
            if (!field.getName().equals("id")) {
                mockReq.param(field.getName(), fieldVal);
            }
        }

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(customer)).thenReturn(customer);

        mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();

    }

    @Test
    @WithMockStaff
    void testSaveUpdateAddressWithStaff() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Address address = data.getAddressList().get(1);

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "address/renew/TestUserId1");
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        for (Field field : address.getClass().getDeclaredFields()) {
            String name = StringUtils.capitalize(field.getName());
            String fieldVal = (String) address.getClass().getMethod(data.isMethodExists(address, "get" + name)
                    ? "get" + name : "is" + name).invoke(address);
            if (!field.getName().equals("id")) {
                mockReq.param(field.getName(), fieldVal);
            }
        }

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(customer)).thenReturn(customer);

        mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();

    }

    @Test
    @WithMockSysadmin
    void testSaveUpdateAddressWithSysadmin() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Address address = data.getAddressList().get(1);

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "address/renew/TestUserId1");
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        for (Field field : address.getClass().getDeclaredFields()) {
            String name = StringUtils.capitalize(field.getName());
            String fieldVal = (String) address.getClass().getMethod(data.isMethodExists(address, "get" + name)
                    ? "get" + name : "is" + name).invoke(address);
            if (!field.getName().equals("id")) {
                mockReq.param(field.getName(), fieldVal);
            }
        }

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(customer)).thenReturn(customer);

        mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();

    }

    @Test
    @WithMockAll
    void testSaveUpdateAddressWithAllRoles() throws Exception {
        Customer customer = data.getCustomerList().get(0);
        Address address = data.getAddressList().get(1);

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders
                .multipart(HttpMethod.PUT, URL + "address/renew/TestUserId1");
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        for (Field field : address.getClass().getDeclaredFields()) {
            String name = StringUtils.capitalize(field.getName());
            String fieldVal = (String) address.getClass().getMethod(data.isMethodExists(address, "get" + name)
                    ? "get" + name : "is" + name).invoke(address);
            if (!field.getName().equals("id")) {
                mockReq.param(field.getName(), fieldVal);
            }
        }

        when(userService.getCustomerById(anyString())).thenReturn(customer);
        when(userService.saveCustomer(customer)).thenReturn(customer);

        mvc.perform(mockReq)

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customer))
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();

    }
}