package com.vzv.shop.service.implementation;

import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.repository.OrderRepository;
import com.vzv.shop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getUnchecked() {
		return orderRepository.findAllByStatus("PROCESSING");
	}

	@Override
	public Integer getUncheckedCount(){
		return orderRepository.getCountByStatus("PROCESSING");
	}

	@Override
	public List<Order> getAllUserOrders(String customerId) {
		return orderRepository.findAllByCustomerId(customerId);
	}

	@Override
	public Customer getCustomerOfOrder(String orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow();
		return order.getCustomer();
	}

	@Override
	public List<Order> getCustomersOrders(String customerId) {
		return orderRepository.findAllByCustomerId(customerId.strip());
	}

	@Override
	public Order getById(String orderId) {
		return orderRepository.getOrderById(orderId).orElse(null);
	}

	@Override
	public List<Order> getByCreated(String date) {
		return orderRepository.findAllByCreated(date);
	}

	@Override
	public List<Order> getByUpdated(String date) {
		return orderRepository.findAllByUpdated(date);
	}

	@Override
	public List<Order> saveOrders(List<Order> orders) {
		return orderRepository.saveAll(orders);
	}

	@Override
	public Order save(Order order) {
		return orderRepository.saveAndFlush(order);
	}

	@Override
	public Order update(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public boolean delete(Order order) {
		orderRepository.delete(order);
		return orderRepository.findById(order.getId()).orElse(null) == null;
	}

	@Override
	public boolean deleteById(String orderId) {
		orderRepository.deleteById(orderId);
		return orderRepository.findById(orderId).orElse(null) == null;
	}

	@Override
	public List<String> getSuggestionsByCriteria(String criteria) {
		return orderRepository.findAll().stream()
				.map(Order::getId)
				.filter(id -> StringUtils.containsIgnoreCase(id, criteria))
				.toList();

	}
}
