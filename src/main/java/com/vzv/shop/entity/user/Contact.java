package com.vzv.shop.entity.user;

import jakarta.el.MethodNotFoundException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CONTACTS")
public class Contact {

	@Id
	private String id;
	private String email;
	private String phone;

	public Contact(String id, MultipartHttpServletRequest request){
		this.id = request.getParameter("id") != null ? request.getParameter("id") : id;
		this.email = request.getParameter("email") != null
				? request.getParameter("email").toLowerCase() : null;
		this.phone = request.getParameter("phone");
	}

	private String reFormatPhone(String phone){
		phone = phone.replaceAll("\\D+", "");
		StringBuilder sb = new StringBuilder();
		sb.append("+").append(phone, 0, 1).append("(").append(phone, 2, 4).append(")")
				.append(phone, 5, 8).append("-").append(phone, 9, 10).append("-")
				.append(phone, 11, 12);
		return sb.toString();
	}

	public Contact invokeMethod(Method method, Object value) throws InvocationTargetException, IllegalAccessException {
		method.invoke(this, value);
		return this;
	}

	public boolean hasProperty(String name){
		try {
			return Stream.of(this.getClass().getDeclaredFields())
					.filter(prop -> prop.getName().equals(name)).toList().size() > 0;
		} catch (MethodNotFoundException e){
			System.out.println("Field: " +  name + " not found!");
		}
		return false;
	}

	public Contact invokeSetter(String name, String value){
		try {
			Method setter = this.getClass().getMethod("set" + StringUtils.capitalize(name), String.class);
			setter.invoke(this, value);
			return this;
		} catch (NoSuchMethodException | RuntimeException | IllegalAccessException | InvocationTargetException e) {
			System.out.println("Error!\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
		}
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Contact contact)) return false;
		return Objects.equals(id, contact.id) && Objects.equals(email, contact.email)
				&& Objects.equals(phone, contact.phone);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, phone);
	}
}