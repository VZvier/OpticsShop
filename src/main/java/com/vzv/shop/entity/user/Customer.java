package com.vzv.shop.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.entity.order.Order;
import jakarta.el.MethodNotFoundException;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
public class Customer {

	@Id
	private String id;
	@Column(name = "First_Name")
	private String fName;
	@Column(name = "Last_Name")
	private String lName;
	@Column(name = "Fathers_Name")
	private String fatherName;
	private String born;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Id", referencedColumnName = "Id")
	private Contact contact;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "Id", referencedColumnName = "Id")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "Id", referencedColumnName = "Id")
	private User user;

	@OneToMany(mappedBy = "customer", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	@ToString.Exclude
	private List<Order> orders;


	public Customer(String id, HttpServletRequest request){
		this.id = request.getParameter("id") != null ? request.getParameter("id") : id;
		this.fName = request.getParameter(request.getParameter("name") != null ? "name" : "fName");
		this.lName = request.getParameter(request.getParameter("surname") != null ? "surname" : "lName");
		this.fatherName = request.getParameter("fatherName");
		this.born = request.getParameter("born");
	}

	public Customer invokeSetter(Method method, Object value) throws InvocationTargetException, IllegalAccessException {
		method.invoke(this, value);
		return this;
	}

	public boolean hasProperty(String name){
		try {
			return Stream.of(this.getClass().getDeclaredFields())
					.filter(prop -> prop.getName().equals(name)).toList().size() > 0;
		} catch (MethodNotFoundException e){
			return false;
		}
	}

	public Customer invokeSetter(String name, String value){
		try {
			Method setter = this.getClass().getMethod("set" + StringUtils.capitalize(name), String.class);
			setter.invoke(this, value);
			return this;
		} catch (NoSuchMethodException | RuntimeException | IllegalAccessException | InvocationTargetException e) {
			String msg = "";
			if (e.getClass() == NoSuchMethodException.class) {
				msg = "Method: " + name + " not found!";
			} else if (e.getClass() == RuntimeException.class) {
				msg = "Runtime exception!";
			} else {
				msg = "Runtime exception!";
			}
		}
		return  this;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id='" + (id != null ? id.strip() : null)+ '\'' +
				", fName='" + ( fName != null ? fName.strip() : null )+ '\'' +
				", lName='" + ( lName != null ? lName.strip() : null ) + '\'' +
				", fatherName='" + ( fatherName != null ? fatherName.strip() : null ) + '\'' +
				", born='" + ( born != null ? born.strip() : null ) + '\'' +
				", contact=" + contact +
				", address=" + address +
				", user=" + user +
				", orders=" + (orders != null ? toStringIds(orders) : null) +
				'}';
	}

	public String toStringFullName(){
		return this.lName + " " + this.fName + " " + this.fatherName;
	}

	private List<String> toStringIds(List<Order> orders){
		return orders.stream().map(Order::getId).toList();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Customer customer)) return false;
		return Objects.equals(id.strip(), customer.id.strip())
				&& Objects.equals(fName.strip(), customer.fName.strip())
				&& Objects.equals(lName.strip(), customer.lName.strip())
				&& Objects.equals(fatherName.strip(), customer.fatherName.strip())
				&& Objects.equals(born.strip(), customer.born.strip())
				&& Objects.equals(contact, customer.contact)
				&& Objects.equals(address, customer.address)
				&& Objects.equals(user, customer.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fName, lName, fatherName, born, contact, address, user);
	}
}