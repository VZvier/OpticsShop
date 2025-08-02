package com.vzv.shop.service;

import java.util.List;

import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.FullUser;

public interface OrderService {

	List<Order> getAll();

	List<Order> getUnchecked();

	Integer getUncheckedCount();

	List<Order> getAllUserOrders(String userId);

	Customer getCustomerOfOrder(String orderId);

	List<Order> getCustomersOrders(String customerId);

	Order getById(String orderId);

	List<Order> getByCreated(String date);

	List<Order> getByUpdated(String date);

	List<Order> saveOrders(List<Order> orders);

	Order save(Order order);

	Order update(Order order);

	boolean delete(Order order);

	boolean deleteById(String orderId);

	List<String> getSuggestionsByCriteria(String criteria);
}
