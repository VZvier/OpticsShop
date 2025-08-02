package com.vzv.shop.authentication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.DataForTests;
import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.rest.AuthController;
import com.vzv.shop.entity.user.Address;
import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.enumerated.Role;
import com.vzv.shop.security.CustomAuthenticationProvider;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@WebMvcTest(AuthController.class)
@Import(ApplicationConfig.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    private final DataForTests data = new DataForTests();

    @MockBean
    private CustomAuthenticationProvider provider;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithAnonymousUser
    void testSignIn() throws Exception {
        List<User> users = data.getUserList();
        log.info("USer: {}", users.get(0));
        UserDetails user = createUserDetails(users.get(0));
        log.info("userDetails: {}, pass: {}, Auth: {}", user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        log.info("Auth: {}", auth);

        when(userService.getByLogin(user.getUsername())).thenReturn(users.get(0));
        when(userService.updateUser(any())).thenReturn(users.get(0));
        when(provider.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())))
                .thenReturn(auth);

        mvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/shop/api/users/login?login="
                                + users.get(0).getLogin() + "&&password=" + users.get(0).getPassword()
                                + "&&positionToReturn="))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testRegister() throws Exception {
        int expectedStatus = 200;
        final Customer expectedObject = data.getCustomerList().get(0);
        Map<String, Object> expectedMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        User user = expectedObject.getUser();
        Address address = expectedObject.getAddress();
        Contact contact = expectedObject.getContact();

        String expectedMessage = "Регисмтрация пользователя "
                + user.getLogin() + " успешна!\n Пожалуйста, " + expectedObject.getFName()
                + " " + expectedObject.getFatherName() + " выполните вход!";
        expectedMap.put("welcomeMessage", expectedMessage);
        expectedMap.put("customer", expectedObject);
        String mapAsString = mapper.writeValueAsString(expectedMap);

        when(userService.generateId()).thenReturn(expectedObject.getId());
        when(userService.save(any(), any(), any(), any())).thenReturn(expectedObject);
        when(userService.getCustomerById(expectedObject.getId())).thenReturn(expectedObject);

        MockMultipartHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.multipart(HttpMethod.POST,
                "/shop/api/users/registration");
        mockReq.accept(MediaType.APPLICATION_JSON_VALUE);

        List<Object> objects = List.of(expectedObject, user, address, contact);
        for (Object obj : objects) {
            List<Field> fields = List.of(obj.getClass().getDeclaredFields());
            for (Field field : fields) {
                if(!field.getName().equals("log")) {
                    String name = StringUtils.capitalize(field.getName());
                    Object value = obj.getClass().getMethod(
                                    (data.isMethodExists(obj, "get" + name) ? "get" + name : "is" + name))
                            .invoke(obj);
                    mockReq.param(field.getName(), String.valueOf(value));
                }
            }
        }

        MvcResult result = mvc.perform(mockReq).andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> actualMap = data.mapFromJson(content, new TypeReference<HashMap<String, Object>>() {});
        expectedMap = data.mapFromJson(mapAsString, new TypeReference<HashMap<String, Object>>() {});
        String actualMessage = (String)actualMap.get("welcomeMessage");

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedMap, actualMap);

    }

    private UserDetails createUserDetails(User user){
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .roles(Arrays.toString(user.getRoles().stream().map(Role::getLabel).toArray()))
                    .build();
        } else {
            log.error("User is null");
        }
        return null;
    }

}