package com.vzv.shop.entity.order;

import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.enumerated.Status;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {

	@Id
	private String id;

	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "Id")
	private Customer customer;

	@OneToMany(targetEntity = OrderLine.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Order_Id", referencedColumnName = "Id")
	private List<OrderLine> orderLines;

	@OneToMany(targetEntity = ConstructorOrderLine.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Order_Id", referencedColumnName = "Id")
	private List<ConstructorOrderLine> constructors;

	private String address;
	private String created;
	private String updated;
	private String status;

	public Order(String id, Customer customer, List<OrderLine> orderLines, List<ConstructorOrderLine> constructors,
				 String address, String created, String updated, Status status) {
		this.id = (id == null ? RandomStringUtils.randomAlphanumeric(12) : id);
		this.customer = customer;
		this.orderLines = orderLines;
		this.constructors = constructors;
		this.address = address;
		this.created = created;
		this.updated = updated;
		this.status = status.getLabel();
	}

	@Override
	public String toString() {
		return "\nOrder{\nid='" + (id != null ? id.strip() : null) + '\'' +
				", customer=\nCustomer{id=" +  customer.getId().trim() + ", login= " +
				customer.getUser().getLogin().trim() + ", fName= " + customer.getFName().strip() + ", lName= " +
				customer.getLName().strip() + ", fatherName= " + customer.getFatherName().strip() + "}" +
				", \nOrderLines " + orderLines.size() + "=" + orderLines +
				", \nConstructors " + constructors.size() + "=" + constructors +
				", \naddress='" + address.strip() + "'" +
				", \ncreated='" + created.strip() + "'" +
				", updated='" + updated.strip() + "'" +
				", status=" + status.strip() +
				"};";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Order order)) return false;
		return Objects.equals(StringUtils.trimToNull(id), StringUtils.trimToNull(order.id))
				&& Objects.equals(StringUtils.trimToNull(customer.getId()), StringUtils.trimToNull(order.customer.getId()))
				&& Objects.equals(orderLines.size(), order.orderLines.size())
				&& Objects.equals(constructors.size(), order.constructors.size())
				&& Objects.equals(StringUtils.trimToNull(address), StringUtils.trimToNull(order.address))
				&& Objects.equals(StringUtils.trimToNull(created), StringUtils.trimToNull(order.created))
				&& Objects.equals(StringUtils.trimToNull(updated), StringUtils.trimToNull(order.updated))
				&& Objects.equals(StringUtils.trimToNull(status), StringUtils.trimToNull(order.status));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, customer.getId(), orderLines, constructors, address, created, updated, status);
	}
}
