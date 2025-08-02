package com.vzv.shop.repository;

import com.vzv.shop.entity.order.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.customer LEFT JOIN FETCH o.orderLines WHERE o.id = ?1")
	Optional<Order> getOrderById(@NotNull String id);

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.customer LEFT JOIN FETCH o.orderLines WHERE o.customer.id = ?1")
	List<Order> findAllByCustomerId(String customerId);

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.customer LEFT JOIN FETCH o.orderLines WHERE o.created LIKE %?1%")
	List<Order> findAllByCreated(String date);

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.customer LEFT JOIN FETCH o.orderLines WHERE o.updated LIKE %?1%")
	List<Order> findAllByUpdated(String date);

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.customer " +
			"LEFT JOIN FETCH o.orderLines WHERE o.status = ?1 OR o.status LIKE %?1%")
	List<Order> findAllByStatus(String status);

	@Query("SELECT count(o) FROM Order o WHERE o.status = ?1")
	Integer getCountByStatus(String status);

}