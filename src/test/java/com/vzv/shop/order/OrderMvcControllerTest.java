package com.vzv.shop.order;

import com.vzv.shop.DataForTests;
import com.vzv.shop.controller.web.OrderMvcController;
import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.ProductService;
import com.vzv.shop.service.UserService;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebAppConfiguration
@WebMvcTest(OrderMvcController.class)
class OrderMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final DataForTests data = new DataForTests();

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithAnonymousUser
    void testGetOrderPageNotAnonymous() throws Exception {
        when(orderService.getById(null)).thenReturn(null);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page"))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testGetOrderPageWithUser() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(null)).thenReturn(null);
        when(orderService.getById(order.getId())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetOrderPageWithoutIdWithStaff() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(null)).thenReturn(null);
        when(orderService.getById(anyString())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/"+ order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetOrderPageWithoutIdWithSysadmin() throws Exception {
        when(orderService.getById(null)).thenReturn(null);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetOrderPageWithoutIdWithAllRoles() throws Exception {
        when(orderService.getById(null)).thenReturn(null);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetOrderPageWithIdWithUserRole() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(anyString())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetOrderPageWithIdWithStaffRole() throws Exception {
        Order order = data.getOrderList().get(0);
        when(orderService.getById(anyString())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetOrderPageWithIdWithSysadminRole() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(anyString())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetOrderPageWithIdWithAllRoles() throws Exception {
        List<Order> orders = data.getOrderList();
        when(orderService.getById(orders.get(0).getId())).thenReturn(orders.get(0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/page/" + orders.get(0).getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testGetCustomerOfOrderPageWithAnonymous() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(orderService.getCustomerOfOrder(customers.get(0).getId())).thenReturn(customers.get(0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/customer/" + customers.get(0).getId()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testGetCustomerOfOrderPageWithUser() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(orderService.getCustomerOfOrder(customers.get(0).getId())).thenReturn(customers.get(0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/customer/" + customers.get(0).getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customers.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetCustomerOfOrderPageWithStaff() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(orderService.getCustomerOfOrder(customers.get(0).getId())).thenReturn(customers.get(0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/customer/" + customers.get(0).getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customers.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetCustomerOfOrderPageWithSysadmin() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(orderService.getCustomerOfOrder(customers.get(0).getId())).thenReturn(customers.get(0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/customer/" + customers.get(0).getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customers.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetCustomerOfOrderPageWithAllRoles() throws Exception {
        List<Customer> customers = data.getCustomerList();

        when(orderService.getCustomerOfOrder(customers.get(0).getId())).thenReturn(customers.get(0));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/customer/" + customers.get(0).getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
                .andExpect(MockMvcResultMatchers.model().attribute("customer", customers.get(0)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/user-info"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetBasketPageWithUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/basket/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetBasketPageWithStaff() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/basket/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetBasketPageWithSysadmin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/basket/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetBasketPageWithAll() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/basket/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/basket-page"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testGetOrdersPageWithAnonymous() throws Exception {
        List<Order> orders = data.getOrderList();
        Order order = orders.get(0);

        List<Order> ordersByCustomer = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCustomer().getId(), order.getCustomer().getId())).toList();
        List<Order> ordersByCreation = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCreated(), order.getCreated())).toList();
        List<Order> ordersByUpdated = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getUpdated(), order.getUpdated())).toList();

        when(orderService.getCustomersOrders(anyString())).thenReturn(ordersByCustomer);
        when(orderService.getByCreated(anyString())).thenReturn(ordersByCreation);
        when(orderService.getByUpdated(anyString())).thenReturn(ordersByUpdated);
        when(orderService.getById(anyString())).thenReturn(order);
        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/all"))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();


        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-user-id/" + order.getCustomer().getId()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_id/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_creation/" + order.getCreated()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_updating/" + order.getUpdated()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testGetOrdersPageWithUser() throws Exception {
        List<Order> orders = data.getOrderList();
        Order order = orders.get(0);

        List<Order> ordersByCustomer = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(order.getCustomer().getId())).toList();
        List<Order> ordersByCreation = data.getOrderList().stream()
                .filter(o -> o.getCreated().equals(order.getCreated())).toList();
        List<Order> ordersByUpdated = data.getOrderList().stream()
                .filter(o -> o.getUpdated().equals(order.getUpdated())).toList();

        when(orderService.getCustomersOrders(anyString())).thenReturn(ordersByCustomer);
        when(orderService.getByCreated(any())).thenReturn(ordersByCreation);
        when(orderService.getByUpdated(any())).thenReturn(ordersByUpdated);
        when(orderService.getById(anyString())).thenReturn(order);
        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-user-id/" + order.getCustomer().getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCustomer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_id/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", List.of(order)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_creation/" + order.getCreated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCreation))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_updating/" + order.getUpdated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByUpdated))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetOrdersPageWithStaff() throws Exception {
        List<Order> orders = data.getOrderList();
        Order order = orders.get(0);

        List<Order> ordersByCustomer = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCustomer().getId(), order.getCustomer().getId())).toList();
        List<Order> ordersByCreation = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCreated(), order.getCreated())).toList();
        List<Order> ordersByUpdated = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getUpdated(), order.getUpdated())).toList();

        when(orderService.getCustomersOrders(anyString())).thenReturn(ordersByCustomer);
        when(orderService.getByCreated(anyString())).thenReturn(ordersByCreation);
        when(orderService.getByUpdated(anyString())).thenReturn(ordersByUpdated);
        when(orderService.getById(anyString())).thenReturn(order);
        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-user-id/" + order.getCustomer().getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCustomer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_id/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", List.of(order)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_creation/" + order.getCreated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCreation))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_updating/" + order.getUpdated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByUpdated))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetOrdersPageWithSysadmin() throws Exception {
        List<Order> orders = data.getOrderList();
        Order order = orders.get(0);

        List<Order> ordersByCustomer = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCustomer().getId(), order.getCustomer().getId())).toList();
        List<Order> ordersByCreation = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCreated(), order.getCreated())).toList();
        List<Order> ordersByUpdated = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getUpdated(), order.getUpdated())).toList();

        when(orderService.getCustomersOrders(anyString())).thenReturn(ordersByCustomer);
        when(orderService.getByCreated(anyString())).thenReturn(ordersByCreation);
        when(orderService.getByUpdated(anyString())).thenReturn(ordersByUpdated);
        when(orderService.getById(anyString())).thenReturn(order);
        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-user-id/" + order.getCustomer().getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCustomer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_id/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", List.of(order)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_creation/" + order.getCreated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCreation))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_updating/" + order.getUpdated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByUpdated))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetOrdersPageMockAllRoles() throws Exception {
        List<Order> orders = data.getOrderList();
        Order order = orders.get(0);

        List<Order> ordersByCustomer = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCustomer().getId(), order.getCustomer().getId())).toList();
        List<Order> ordersByCreation = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getCreated(), order.getCreated())).toList();
        List<Order> ordersByUpdated = data.getOrderList().stream()
                .filter(o -> Objects.equals(o.getUpdated(), order.getUpdated())).toList();

        when(orderService.getCustomersOrders(anyString())).thenReturn(ordersByCustomer);
        when(orderService.getByCreated(anyString())).thenReturn(ordersByCreation);
        when(orderService.getByUpdated(anyString())).thenReturn(ordersByUpdated);
        when(orderService.getById(anyString())).thenReturn(order);
        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/all"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by-user-id/" + order.getCustomer().getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCustomer))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_id/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", List.of(order)))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_creation/" + order.getCreated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByCreation))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/by_date_of_updating/" + order.getUpdated()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", ordersByUpdated))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testGetOrdersPerMonthOrYearWithAnonymous() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/month"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/year"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testGetOrdersPerMonthOrYearWithUser() throws Exception {
        List<Order> orders = data.getOrderList();

        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/month"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/year"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetOrdersPerMonthOrYearWithStaff() throws Exception {
        List<Order> orders = data.getOrderList();

        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/month"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/year"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetOrdersPerMonthOrYearWithSysadin() throws Exception {
        List<Order> orders = data.getOrderList();

        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/month"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/year"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetOrdersPerMonthOrYearWithAll() throws Exception {
        List<Order> orders = data.getOrderList();

        when(orderService.getAll()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/month"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/per/year"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testGetUncheckedOrdersWithAnonymous() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/unchecked"))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testGetUncheckedOrdersWithMockUser() throws Exception {
        List<Order> orders = data.getOrderList().stream().filter(o -> Objects.equals(o.getStatus(), "PROCESSING")).toList();

        when(orderService.getUnchecked()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/unchecked"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetUncheckedOrdersWithMockStaff() throws Exception {
        List<Order> orders = data.getOrderList().stream().filter(o -> Objects.equals(o.getStatus(), "PROCESSING")).toList();

        when(orderService.getUnchecked()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/unchecked"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetUncheckedOrdersWithMockSysadmin() throws Exception {
        List<Order> orders = data.getOrderList().stream().filter(o -> Objects.equals(o.getStatus(), "PROCESSING")).toList();

        when(orderService.getUnchecked()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/unchecked"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetUncheckedOrdersWithAll() throws Exception {
        List<Order> orders = data.getOrderList().stream().filter(o -> Objects.equals(o.getStatus(), "PROCESSING")).toList();

        when(orderService.getUnchecked()).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/unchecked"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithUser
    void testGetCustomersOrdersWithUser() throws Exception {
        List<Order> orders = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(data.getOrderList().get(0).getCustomer().getId()))
                .toList();


        when(userService.getByLogin(anyString())).thenReturn(orders.get(0).getCustomer().getUser());
        when(orderService.getCustomersOrders(orders.get(0).getCustomer().getId())).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/my-orders"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetCustomersOrdersWithStaff() throws Exception {
        List<Order> orders = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(data.getOrderList().get(0).getCustomer().getId()))
                .toList();


        when(userService.getByLogin(anyString())).thenReturn(data.getOrderList().get(0).getCustomer().getUser());
        when(orderService.getCustomersOrders(anyString())).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/my-orders"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetCustomersOrdersWithSysadmin() throws Exception {
        List<Order> orders = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(data.getOrderList().get(0).getCustomer().getId()))
                .toList();


        when(userService.getByLogin(anyString())).thenReturn(orders.get(0).getCustomer().getUser());
        when(orderService.getCustomersOrders(anyString())).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/my-orders"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetCustomersOrdersWithAll() throws Exception {
        List<Order> orders = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(data.getOrderList().get(0).getCustomer().getId()))
                .toList();

        when(userService.getByLogin(anyString())).thenReturn(orders.get(0).getCustomer().getUser());
        when(orderService.getCustomersOrders(anyString())).thenReturn(orders);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/my-orders"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderList", orders))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/orders"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testGetCreateOrderPageWithAnonymous() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/new"))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testGetCreateOrderPageWithUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_create"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testGetCreateOrderPageWithStaff() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_create"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testGetCreateOrderPageWithSysadmin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_create"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testGetCreateOrderPageWithAll() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_create"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testUpdatePageWithAnonymousUser() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/renew/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testUpdatePageWithUser() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/renew/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_update_page"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testUpdatePageWithStaff() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/renew/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_update_page"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testUpdatePageWithSysadmin() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/renew/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_update_page"))
                .andReturn();
    }

    @Test
    @WithMockAll
    void testUpdatePageWithAll() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/renew/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/staff/order_update_page"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void testSavedOrderPageWithAnonymous() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/saved-order/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUser
    void testSavedOrderPageWithUser() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/saved-order/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/orders"))
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testSavedOrderPageWithStaff() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/saved-order/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/orders"))
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testSavedOrderPageWithSysadmin() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/saved-order/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/orders"))
                .andReturn();
    }


    @Test
    @WithMockAll
    void testSavedOrderPageWithAllUsers() throws Exception {
        Order order = data.getOrderList().get(0);

        when(orderService.getById(order.getId())).thenReturn(order);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/shop/orders/saved-order/" + order.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("order"))
                .andExpect(MockMvcResultMatchers.model().attribute("order", order))
                .andExpect(MockMvcResultMatchers.view().name("pages/orders"))
                .andReturn();
    }

}