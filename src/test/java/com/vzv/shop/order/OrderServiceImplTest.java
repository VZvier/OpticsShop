package com.vzv.shop.order;

import com.vzv.shop.DataForTests;
import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.repository.OrderRepository;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.implementation.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl service;

    private final DataForTests data = new DataForTests();


    @Test
    void testGetAll() {
        List<Order> expectedResult = data.getOrderList();

        when(orderRepository.findAll()).thenReturn(expectedResult);
        List<Order> actualResult = service.getAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetUnchecked() {
        List<Order> expectedResult = data.getOrderList().stream().filter(o -> o.getStatus().equals("PROCESSING")).toList();

        when(orderRepository.findAllByStatus("PROCESSING")).thenReturn(expectedResult);
        List<Order> actualResult = service.getUnchecked();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetUncheckedCount() {
        List<Order> orders = data.getOrderList().stream().filter(o -> o.getStatus().equals("PROCESSING")).toList();
        Integer expectedResult = orders.size();

        when(orderRepository.getCountByStatus("PROCESSING")).thenReturn(expectedResult);
        Integer actualResult = service.getUncheckedCount();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllUserOrders() {
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(customer.getId())).toList();

                when(orderRepository.findAllByCustomerId(customer.getId())).thenReturn(expectedResult);
        List<Order> actualResult = service.getAllUserOrders(customer.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCustomerOfOrder() {
        Order order = data.getOrderList().get(0);
        Customer expectedResult = order.getCustomer();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Customer actualResult = service.getCustomerOfOrder(order.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCustomersOrders() {
        Customer customer = data.getCustomerList().get(0);
        List<Order> expectedResult = data.getOrderList().stream()
                .filter(o -> o.getCustomer().getId().equals(customer.getId())).toList();

        when(orderRepository.findAllByCustomerId(customer.getId())).thenReturn(expectedResult);
        List<Order> actualResult = service.getCustomersOrders(customer.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetById() {
        Order expectedResult = data.getOrderList().get(0);

        when(orderRepository.getOrderById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        Order actualResult = service.getById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByCreated() {
        List<Order> expectedResult = data.getOrderList();

        when(orderRepository.findAllByCreated(anyString())).thenReturn(expectedResult);
        List<Order> actualResult = service.getByCreated(anyString());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByUpdated() {
        List<Order> expectedResult = data.getOrderList();

        when(orderRepository.findAllByUpdated(anyString())).thenReturn(expectedResult);
        List<Order> actualResult = service.getByUpdated(anyString());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveOrders() {
        List<Order> expectedResult = data.getOrderList();

        when(orderRepository.saveAll(anyList())).thenReturn(expectedResult);
        List<Order> actualResult = service.saveOrders(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSave() {
        Order expectedResult = data.getOrderList().get(0);

        when(orderRepository.saveAndFlush(any())).thenReturn(expectedResult);
        Order actualResult = service.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdate() {
        Order expectedResult = data.getOrderList().get(0);

        when(orderRepository.save(any())).thenReturn(expectedResult);
        Order actualResult = service.update(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDelete() {
        Order order = data.getOrderList().get(0);
        boolean expectedResult = true;

        doNothing().when(orderRepository).delete(any());
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        boolean actualResult = service.delete(order);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteById() {
        Order order = data.getOrderList().get(0);
        boolean expectedResult = true;

        doNothing().when(orderRepository).deleteById(anyString());
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        boolean actualResult = service.deleteById(order.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetSuggestionsByCriteria() {
        String criteria = data.getOrderList().get(0).getId().substring(0,3);
        List<String> expectedResult = data.getOrderList().stream().map(Order::getId)
                .filter(id -> id.contains(criteria)).toList();

        when(orderRepository.findAll()).thenReturn(data.getOrderList());
        List<String> actualResult = service.getSuggestionsByCriteria(criteria);

        assertEquals(expectedResult, actualResult);
    }
}