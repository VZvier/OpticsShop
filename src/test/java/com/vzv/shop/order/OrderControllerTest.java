package com.vzv.shop.order;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.DataForTests;
import com.vzv.shop.controller.rest.OrderController;
import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import com.vzv.shop.entity.order.ConstructorOrderLine;
import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.order.OrderLine;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.request.ConstructorRequest;
import com.vzv.shop.request.OrderLineRequest;
import com.vzv.shop.service.GlassesConstructorService;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.ProductService;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Slf4j
@WebAppConfiguration
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private GlassesConstructorService constructorService;

    @MockBean
    private UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockedStatic<AuxiliaryUtils> mockAuxUtils;
    MockedStatic<RandomStringUtils> mockMakeId;

    private final DataForTests data = new DataForTests();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        try {
            mockAuxUtils = mockStatic(AuxiliaryUtils.class);
            mockMakeId = mockStatic(RandomStringUtils.class);
        } catch (Exception e) {
            mockAuxUtils.close();
            mockMakeId.close();
            mockAuxUtils = mockStatic(AuxiliaryUtils.class);
            mockMakeId = mockStatic(RandomStringUtils.class);
        }
    }

    @AfterEach
    void tearDown() {
        mockAuxUtils.close();
        mockMakeId.close();
    }


    @Test
    @WithAnonymousUser
    void testGetByIdWithAnonymous() throws Exception {
        int expectedStatus = 401;
        Order order = data.getOrderList().get(0);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-id/" + order.getId()))
                .andReturn();
        int actualStatus = result.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithUser
    void testGetByIdWithUser() throws Exception {
        int expectedStatus = 200;
        Order expectedResult = data.getOrderList().get(0);

        when(orderService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-id/" + expectedResult.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testGetByIdWithStaff() throws Exception {
        int expectedStatus = 200;
        Order expectedResult = data.getOrderList().get(0);

        when(orderService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-id/" + expectedResult.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testGetByIdWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Order expectedResult = data.getOrderList().get(0);

        when(orderService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-id/" + expectedResult.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testGetByIdWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Order expectedResult = data.getOrderList().get(0);

        when(orderService.getById(expectedResult.getId())).thenReturn(expectedResult);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-id/" + expectedResult.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithAnonymousUser
    void testGetUsersOrdersWithAnonymous() throws Exception {
        int expectedStatus = 401;
        User user = data.getOrderList().get(0).getCustomer().getUser();
        List<Order> expected = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(user.getId())).toList();

        when(orderService.getAllUserOrders(user.getId())).thenReturn(expected);

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/of-user/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithUser
    void testGetUsersOrdersWithUser() throws Exception {
        int expectedStatus = 200;
        User user = data.getOrderList().get(0).getCustomer().getUser();
        List<Order> expectedResult = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(user.getId())).toList();

        when(orderService.getAllUserOrders(user.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/of-user/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order[] actualResult = mapFromJson(content, Order[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, Arrays.stream(actualResult).toList());
    }

    @Test
    @WithMockStaff
    void testGetUsersOrdersWithStaff() throws Exception {
        int expectedStatus = 200;
        User user = data.getOrderList().get(0).getCustomer().getUser();
        List<Order> expectedResult = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(user.getId())).toList();

        when(orderService.getAllUserOrders(user.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/of-user/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order[] actualResult = mapFromJson(content, Order[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, Arrays.stream(actualResult).toList());
    }

    @Test
    @WithMockSysadmin
    void testGetUsersOrdersWithSysadmin() throws Exception {
        int expectedStatus = 200;
        User user = data.getOrderList().get(0).getCustomer().getUser();
        List<Order> expectedResult = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(user.getId())).toList();

        when(orderService.getAllUserOrders(user.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/of-user/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order[] actualResult = mapFromJson(content, Order[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, Arrays.stream(actualResult).toList());
    }

    @Test
    @WithMockAll
    void testGetUsersOrdersWithAllRoles() throws Exception {
        User user = data.getOrderList().get(0).getCustomer().getUser();
        int expectedStatus = 200;
        List<Order> expectedResult = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(user.getId())).toList();

        when(orderService.getAllUserOrders(user.getId())).thenReturn(expectedResult);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/of-user/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order[] actualResult = mapFromJson(content, Order[].class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, Arrays.stream(actualResult).toList());
    }

    @Test
    @WithAnonymousUser
    void testCreateOrderWithAnonymous() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Order order = data.getOrderList().get(0);
        int expectedStatus = 401;

        List<OrderLineRequest> olrList = data.getOLRtList().stream()
                .filter(olr -> order.getOrderLines().stream()
                        .map(ol -> ol.getProduct().getId()).toList().contains(olr.getProductId()))
                .toList();
        List<ConstructorRequest> constrReqList = data.getConstructorRequestList().stream()
                .filter(cReq -> {
                    List<String> ids = order.getConstructors().stream().flatMap(c ->
                            Stream.of(c.getConstructor().getFrame().getId(),
                                    c.getConstructor().getOdLens().getId(),
                                    c.getConstructor().getOsLens().getId())).toList();
                    return ids.contains(cReq.getFrame()) && ids.contains(cReq.getOsLens())
                            && ids.contains(cReq.getOdLens());
                }).toList();
        List<ProductDTO> constrProducts = data.getProductDTOList().stream().filter(p -> olrList.stream()
                .map(OrderLineRequest::getProductId).toList().contains(p.getId())).toList();
        List<ProductDTO> olProducts = data.getProductDTOList().stream().filter(p -> constrReqList.stream()
                        .flatMap(c -> Stream.of(c.getFrame(), c.getOsLens(), c.getOdLens())).toList().contains(p.getId()))
                .toList();
        Customer customer = order.getCustomer();

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/orders/new");
        multiReqBuilder.header("contentType", "application/json")
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(constrReqList))
                .param("customerId", mapper.writeValueAsString(customer.getId()))
                .param("address", mapper.writeValueAsString(customer.getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        when(productService.getAllByIds(constrReqList.stream().flatMap(c -> Stream.
                of(c.getFrame(), c.getOsLens(), c.getOdLens())).toList()))
                .thenReturn(constrProducts);
        when(productService.getAllByIds(olrList.stream().map(OrderLineRequest::getProductId).toList()))
                .thenReturn(olProducts);

        MvcResult mvc = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithUser
    void testCreateOrderWithUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        String expectedResult = order.getId();

        List<OrderLineRequest> olrList = order.getOrderLines().stream().flatMap(ol -> data.getOLRtList().stream()
                .filter(olReq -> ol.getProduct().getId().equals(olReq.getProductId())
                        && Integer.parseInt(ol.getQuantity()) == olReq.getAmount())).toList();

        List<ConstructorRequest> constrReqList = order.getConstructors().stream().flatMap(oCon ->
                data.getConstructorRequestList().stream().filter(cReq ->
                        cReq.getFrame().equals(oCon.getConstructor().getFrame().getId()) &&
                                cReq.getOsLens().equals(oCon.getConstructor().getOsLens().getId()) &&
                                cReq.getOdLens().equals(oCon.getConstructor().getOdLens().getId())
                )
        ).toList();
        List<GlassesConstructor> constructors = order.getConstructors().stream()
                .map(ConstructorOrderLine::getConstructor).toList();

        List<ProductDTO> olProducts = data.getProductDTOList().stream().filter(p -> olrList.stream()
                .map(OrderLineRequest::getProductId).toList().contains(p.getId())).toList();

        List<ProductDTO> constrProducts = constrReqList.stream().flatMap(c -> data.getProductDTOList().stream()
                        .filter(p -> List.of(c.getFrame(), c.getOdLens(), c.getOdLens()).contains(p.getId())))
                .collect(Collectors.toSet()).stream().toList();

        Customer customer = order.getCustomer();

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/orders/new");
        multiReqBuilder.header("contentType", "application/json")
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(constrReqList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(customer.getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMakeId.when(() -> RandomStringUtils.randomAlphanumeric(12)).thenReturn(order.getId());
        when(productService.getAllByIds(constrReqList.stream().flatMap(c ->
                Stream.of(c.getFrame(), c.getOsLens(), c.getOdLens())).toList())).thenReturn(constrProducts);

        mockAuxUtils.when(() -> AuxiliaryUtils.makeConstructorOrderLines(anyList(), anyList()))
                .thenReturn(constructors);

        when(constructorService.saveAll(constructors)).thenReturn(constructors);
        when(userService.getByLogin(anyString()))
                .thenReturn(order.getCustomer().getUser());
        when(userService.getCustomerById(anyString())).thenReturn(order.getCustomer());
        when(productService.getAllByIds(anyList())).thenReturn(olProducts);
        when(orderService.save(any())).thenReturn(order);

        MvcResult mvc = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String actualResult = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testCreateOrderWithStaff() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        String expectedResult = order.getId();

        List<OrderLineRequest> olrList = order.getOrderLines().stream().flatMap(ol -> data.getOLRtList().stream()
                .filter(olReq -> ol.getProduct().getId().equals(olReq.getProductId())
                        && Integer.parseInt(ol.getQuantity()) == olReq.getAmount())).toList();

        List<ConstructorRequest> constrReqList = order.getConstructors().stream().flatMap(oCon ->
                data.getConstructorRequestList().stream().filter(cReq ->
                        cReq.getFrame().equals(oCon.getConstructor().getFrame().getId()) &&
                                cReq.getOsLens().equals(oCon.getConstructor().getOsLens().getId()) &&
                                cReq.getOdLens().equals(oCon.getConstructor().getOdLens().getId())
                )
        ).toList();
        List<GlassesConstructor> constructors = order.getConstructors().stream()
                .map(ConstructorOrderLine::getConstructor).toList();

        List<ProductDTO> olProducts = data.getProductDTOList().stream().filter(p -> olrList.stream()
                .map(OrderLineRequest::getProductId).toList().contains(p.getId())).toList();

        List<ProductDTO> constrProducts = constrReqList.stream().flatMap(c -> data.getProductDTOList().stream()
                        .filter(p -> List.of(c.getFrame(), c.getOdLens(), c.getOdLens()).contains(p.getId())))
                .collect(Collectors.toSet()).stream().toList();

        Customer customer = order.getCustomer();

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/orders/new");
        multiReqBuilder.header("contentType", "application/json")
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(constrReqList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(customer.getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMakeId.when(() -> RandomStringUtils.randomAlphanumeric(12)).thenReturn(order.getId());
        when(productService.getAllByIds(constrReqList.stream().flatMap(c ->
                Stream.of(c.getFrame(), c.getOsLens(), c.getOdLens())).toList())).thenReturn(constrProducts);
        mockAuxUtils.when(() -> AuxiliaryUtils.makeConstructorOrderLines(anyList(), anyList()))
                .thenReturn(constructors);

        when(constructorService.saveAll(constructors)).thenReturn(constructors);

        when(userService.getByLogin(anyString()))
                .thenReturn(order.getCustomer().getUser());
        when(userService.getCustomerById(anyString())).thenReturn(order.getCustomer());
        when(productService.getAllByIds(anyList())).thenReturn(olProducts);
        when(orderService.save(any())).thenReturn(order);

        MvcResult mvc = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String actualResult = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testCreateOrderWithSysadmin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        String expectedResult = order.getId();

        List<OrderLineRequest> olrList = order.getOrderLines().stream().flatMap(ol -> data.getOLRtList().stream()
                .filter(olReq -> ol.getProduct().getId().equals(olReq.getProductId())
                        && Integer.parseInt(ol.getQuantity()) == olReq.getAmount())).toList();

        List<ConstructorRequest> constrReqList = order.getConstructors().stream().flatMap(oCon ->
                data.getConstructorRequestList().stream().filter(cReq ->
                        cReq.getFrame().equals(oCon.getConstructor().getFrame().getId()) &&
                                cReq.getOsLens().equals(oCon.getConstructor().getOsLens().getId()) &&
                                cReq.getOdLens().equals(oCon.getConstructor().getOdLens().getId())
                )
        ).toList();
        List<GlassesConstructor> constructors = order.getConstructors().stream()
                .map(ConstructorOrderLine::getConstructor).toList();

        List<ProductDTO> olProducts = data.getProductDTOList().stream().filter(p -> olrList.stream()
                .map(OrderLineRequest::getProductId).toList().contains(p.getId())).toList();

        List<ProductDTO> constrProducts = constrReqList.stream().flatMap(c -> data.getProductDTOList().stream()
                        .filter(p -> List.of(c.getFrame(), c.getOdLens(), c.getOdLens()).contains(p.getId())))
                .collect(Collectors.toSet()).stream().toList();

        Customer customer = order.getCustomer();

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/orders/new");
        multiReqBuilder.header("contentType", "application/json")
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(constrReqList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(customer.getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMakeId.when(() -> RandomStringUtils.randomAlphanumeric(12)).thenReturn(order.getId());

        when(productService.getAllByIds(constrReqList.stream().flatMap(c ->
                Stream.of(c.getFrame(), c.getOsLens(), c.getOdLens())).toList())).thenReturn(constrProducts);
        mockAuxUtils.when(() -> AuxiliaryUtils.makeConstructorOrderLines(anyList(), anyList()))
                .thenReturn(constructors);

        when(constructorService.saveAll(constructors)).thenReturn(constructors);
        when(userService.getByLogin(anyString()))
                .thenReturn(order.getCustomer().getUser());
        when(userService.getCustomerById(anyString())).thenReturn(order.getCustomer());
        when(productService.getAllByIds(anyList())).thenReturn(olProducts);
        when(orderService.save(any())).thenReturn(order);

        MvcResult mvc = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String actualResult = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testCreateOrderWithAllRoles() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        String expectedResult = order.getId();

        List<OrderLineRequest> olrList = order.getOrderLines().stream().flatMap(ol -> data.getOLRtList().stream()
                .filter(olReq -> ol.getProduct().getId().equals(olReq.getProductId())
                        && Integer.parseInt(ol.getQuantity()) == olReq.getAmount())).toList();

        List<ConstructorRequest> constrReqList = order.getConstructors().stream().flatMap(oCon ->
                data.getConstructorRequestList().stream().filter(cReq ->
                        cReq.getFrame().equals(oCon.getConstructor().getFrame().getId()) &&
                                cReq.getOsLens().equals(oCon.getConstructor().getOsLens().getId()) &&
                                cReq.getOdLens().equals(oCon.getConstructor().getOdLens().getId())
                )
        ).toList();
        List<GlassesConstructor> constructors = order.getConstructors().stream()
                .map(ConstructorOrderLine::getConstructor).toList();

        List<ProductDTO> olProducts = data.getProductDTOList().stream().filter(p -> olrList.stream()
                .map(OrderLineRequest::getProductId).toList().contains(p.getId())).toList();

        List<ProductDTO> constrProducts = constrReqList.stream().flatMap(c -> data.getProductDTOList().stream()
                        .filter(p -> List.of(c.getFrame(), c.getOdLens(), c.getOdLens()).contains(p.getId())))
                .collect(Collectors.toSet()).stream().toList();

        Customer customer = order.getCustomer();

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/shop/orders/new");
        multiReqBuilder.header("contentType", "application/json")
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(constrReqList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(customer.getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMakeId.when(() -> RandomStringUtils.randomAlphanumeric(12)).thenReturn(order.getId());
        when(productService.getAllByIds(constrReqList.stream().flatMap(c ->
                Stream.of(c.getFrame(), c.getOsLens(), c.getOdLens())).toList())).thenReturn(constrProducts);
        mockAuxUtils.when(() -> AuxiliaryUtils.makeConstructorOrderLines(anyList(), anyList()))
                .thenReturn(constructors);

        when(constructorService.saveAll(constructors)).thenReturn(constructors);
        when(userService.getByLogin(anyString()))
                .thenReturn(order.getCustomer().getUser());
        when(userService.getCustomerById(anyString())).thenReturn(order.getCustomer());
        when(productService.getAllByIds(anyList())).thenReturn(olProducts);
        when(orderService.save(any())).thenReturn(order);

        MvcResult mvc = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String actualResult = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithAnonymousUser
    void testUpdateOrderWithAnonymous() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 401;
        Order order = data.getOrderList().get(0);
        List<OrderLineRequest> olrList = data.getOLRtList().subList(2, 4);
        List<OrderLine> olist = data.getOLines().subList(2, 4);
        List<ConstructorOrderLine> newConstrList = data.getConstrOLines().subList(2, 4);
        Order expectedResult = data.getOrderList().get(0);
        expectedResult.setOrderLines(Stream.concat(expectedResult.getOrderLines().stream(),
                olist.stream()).collect(Collectors.toList()));
        expectedResult.setConstructors(Stream.concat(expectedResult.getConstructors().stream(),
                newConstrList.stream()).collect(Collectors.toList()));

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/orders/renew");
        multiReqBuilder.header("contentType", "application/json")
                .param("order", mapper.writeValueAsString(order))
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(newConstrList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(order.getCustomer().getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        when(orderService.getById(anyString())).thenReturn(expectedResult);
        when(productService.getAllByIds(anyList())).thenReturn(data.getProductDTOList());
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any())).thenReturn(expectedResult);

        when(orderService.update(any())).thenReturn(expectedResult);

        MvcResult mvcRes = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvcRes.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithUser
    void testUpdateOrderWithUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        List<OrderLineRequest> olrList = data.getOLRtList().subList(2, 4);
        List<OrderLine> olist = data.getOLines().subList(2, 4);
        List<ConstructorOrderLine> newConstrList = data.getConstrOLines().subList(2, 4);
        Order expectedResult = data.getOrderList().get(0);
        expectedResult.setOrderLines(Stream.concat(expectedResult.getOrderLines().stream(),
                olist.stream()).collect(Collectors.toList()));
        expectedResult.setConstructors(Stream.concat(expectedResult.getConstructors().stream(),
                newConstrList.stream()).collect(Collectors.toList()));

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/orders/renew");
        multiReqBuilder.header("contentType", "application/json")
                .param("order", mapper.writeValueAsString(order))
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(newConstrList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(order.getCustomer().getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        when(orderService.getById(anyString())).thenReturn(expectedResult);
        when(productService.getAllByIds(anyList())).thenReturn(data.getProductDTOList());
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any())).thenReturn(expectedResult);

        when(orderService.update(any())).thenReturn(expectedResult);

        MvcResult mvcRes = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvcRes.getResponse().getStatus();
        String content = mvcRes.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testUpdateOrderWithStaff() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        List<OrderLineRequest> olrList = data.getOLRtList().subList(2, 4);
        List<OrderLine> olist = data.getOLines().subList(2, 4);
        List<ConstructorOrderLine> newConstrList = data.getConstrOLines().subList(2, 4);
        Order expectedResult = data.getOrderList().get(0);
        expectedResult.setOrderLines(Stream.concat(expectedResult.getOrderLines().stream(),
                olist.stream()).collect(Collectors.toList()));
        expectedResult.setConstructors(Stream.concat(expectedResult.getConstructors().stream(),
                newConstrList.stream()).collect(Collectors.toList()));

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/orders/renew");
        multiReqBuilder.header("contentType", "application/json")
                .param("order", mapper.writeValueAsString(order))
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(newConstrList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(order.getCustomer().getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        when(orderService.getById(anyString())).thenReturn(expectedResult);
        when(productService.getAllByIds(anyList())).thenReturn(data.getProductDTOList());
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any())).thenReturn(expectedResult);

        when(orderService.update(any())).thenReturn(expectedResult);

        MvcResult mvcRes = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvcRes.getResponse().getStatus();
        String content = mvcRes.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testUpdateOrderWithSysadmin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        int expectedStatus = 200;
        Order order = data.getOrderList().get(0);
        List<OrderLineRequest> olrList = data.getOLRtList().subList(2, 4);
        List<OrderLine> olist = data.getOLines().subList(2, 4);
        List<ConstructorOrderLine> newConstrList = data.getConstrOLines().subList(2, 4);
        Order expectedResult = data.getOrderList().get(0);
        expectedResult.setOrderLines(Stream.concat(expectedResult.getOrderLines().stream(),
                olist.stream()).collect(Collectors.toList()));
        expectedResult.setConstructors(Stream.concat(expectedResult.getConstructors().stream(),
                newConstrList.stream()).collect(Collectors.toList()));

        MockMultipartHttpServletRequestBuilder multiReqBuilder =
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/shop/orders/renew");
        multiReqBuilder.header("contentType", "application/json")
                .param("order", mapper.writeValueAsString(order))
                .param("orderLines[]", mapper.writeValueAsString(olrList))
                .param("constructors[]", mapper.writeValueAsString(newConstrList))
                .param("customerId", mapper.writeValueAsString(order.getCustomer().getId()))
                .param("address", mapper.writeValueAsString(order.getCustomer().getAddress().toInlineOrEmpty()))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        when(orderService.getById(anyString())).thenReturn(expectedResult);
        when(productService.getAllByIds(anyList())).thenReturn(data.getProductDTOList());
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any())).thenReturn(expectedResult);

        when(orderService.update(any())).thenReturn(expectedResult);

        MvcResult mvcRes = this.mockMvc.perform(multiReqBuilder.with(csrf()))
                .andReturn();

        int actualStatus = mvcRes.getResponse().getStatus();
        String content = mvcRes.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Order actualResult = mapFromJson(content, Order.class);

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithAnonymousUser
    void testFindOrdersOfCustomerWithAnonymous() throws Exception {
        int expectedStatus = 401;
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList()
                .stream().filter(o -> o.getCustomer().equals(customer)).toList();

        when(orderService.getCustomersOrders(customer.getId())).thenReturn(expectedResult);

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/customer/" + customer.getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithUser
    void testFindOrdersOfCustomerWithUser() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList()
                .stream().filter(o -> o.getCustomer().equals(customer)).toList();

        when(orderService.getCustomersOrders(customer.getId())).thenReturn(expectedResult);

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/customer/" + customer.getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = Arrays.asList(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testFindOrdersOfCustomerWithStaff() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList()
                .stream().filter(o -> o.getCustomer().equals(customer)).toList();

        when(orderService.getCustomersOrders(customer.getId())).thenReturn(expectedResult);

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/customer/" + customer.getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = Arrays.asList(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testFindOrdersOfCustomerWithSysadmin() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList()
                .stream().filter(o -> o.getCustomer().equals(customer)).toList();

        when(orderService.getCustomersOrders(customer.getId())).thenReturn(expectedResult);

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/customer/" + customer.getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = Arrays.asList(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testFindOrdersOfCustomerWithAllRoles() throws Exception {
        int expectedStatus = 200;
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList()
                .stream().filter(o -> o.getCustomer().equals(customer)).toList();

        when(orderService.getCustomersOrders(customer.getId())).thenReturn(expectedResult);

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/customer/" + customer.getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = Arrays.asList(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithAnonymousUser
    void testFindOrderByIDWithAnonymous() throws Exception {
        int expectedStatus = 401;
        List<Order> expectedResult = List.of(data.getOrderList().get(0));

        when(orderService.getById(expectedResult.get(0).getId())).thenReturn(expectedResult.get(0));

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/order/" + expectedResult.get(0).getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @WithUser
    void testFindOrderByIDWithUser() throws Exception {
        int expectedStatus = 200;
        List<Order> expectedResult = List.of(data.getOrderList().get(0));

        when(orderService.getById(expectedResult.get(0).getId())).thenReturn(expectedResult.get(0));

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/order/" + expectedResult.get(0).getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = List.of(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockStaff
    void testFindOrderByIDWithStaff() throws Exception {
        int expectedStatus = 200;
        List<Order> expectedResult = List.of(data.getOrderList().get(0));

        when(orderService.getById(expectedResult.get(0).getId())).thenReturn(expectedResult.get(0));

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/order/" + expectedResult.get(0).getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = List.of(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockSysadmin
    void testFindOrderByIDWithSysadmin() throws Exception {
        int expectedStatus = 200;
        List<Order> expectedResult = List.of(data.getOrderList().get(0));

        when(orderService.getById(expectedResult.get(0).getId())).thenReturn(expectedResult.get(0));

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/order/" + expectedResult.get(0).getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = List.of(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @WithMockAll
    void testFindOrderByIDWithAllRoles() throws Exception {
        int expectedStatus = 200;
        List<Order> expectedResult = List.of(data.getOrderList().get(0));

        when(orderService.getById(expectedResult.get(0).getId())).thenReturn(expectedResult.get(0));

        MvcResult mvc = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/search/order/" + expectedResult.get(0).getId()))
                .andReturn();

        int actualStatus = mvc.getResponse().getStatus();
        String content = mvc.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Order> actualResult = List.of(mapFromJson(content, Order[].class));

        assertEquals(expectedStatus, actualStatus);
        assertEquals(expectedResult, actualResult);
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(json, clazz);
    }
}