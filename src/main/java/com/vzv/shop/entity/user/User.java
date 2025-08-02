package com.vzv.shop.entity.user;

import com.vzv.shop.enumerated.Role;
import jakarta.el.MethodNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USRS")
public class User {

	@Id
	private String id;
	private String login;
	private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES", joinColumns = @JoinColumn(name = "User_Id", referencedColumnName = "Id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
	private Set<Role> roles;
	private String created;
	private String lastVisit;

	public User(String id, String login, String password, Set<Role> roles) {
		this.id = id.trim();
		this.login = login.trim();
		this.password = password.trim();
		this.roles = roles;
		this.created = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now());
		this.lastVisit = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now());

	}

	public User(String id, String login, String password, Set<Role> roles, String created, String lastVisit) {
		this.id = id.trim();
		this.login = login.trim();
		this.password = password.trim();
		this.roles = roles;
		this.created = created;
		this.lastVisit = lastVisit;

	}

	public User(String id, MultipartHttpServletRequest request){
		this.id = request.getParameter("id") != null ? request.getParameter("id") : id;
		this.login = request.getParameter("login");
		this.password = request.getParameter("pass");
		this.roles = Set.of(Role.getByLabel(request.getParameter("authority")) != null?
				Role.getByLabel(request.getParameter("authority"))
				: Role.USER);
		this.created = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		this.lastVisit = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}


	public User invokeMethod(Method method, Object value) throws InvocationTargetException, IllegalAccessException {
		method.invoke(this, value);
		return this;
	}

	public boolean hasProperty(String name){
		try {
			return Stream.of(this.getClass().getDeclaredFields())
					.filter(prop -> prop.getName().equals(name)).toList().size() > 0;
		} catch (MethodNotFoundException e){
			System.out.println("Field: " + name + " not found!");
		}
		return false;
	}

	public User invokeSetter(String name, String value){
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
			System.out.println("Error! " + msg + "\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
		}
		return this;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", roles=" + roles +
				", created='" + created + '\'' +
				", lastVisit='" + lastVisit + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User user)) return false;
		return Objects.equals(StringUtils.trimToNull(id), StringUtils.trimToNull(user.id))
				&& Objects.equals(StringUtils.trimToNull(login), StringUtils.trimToNull(user.login))
				&& Objects.equals(StringUtils.trimToNull(password), StringUtils.trimToNull(user.password))
				&& Objects.equals(roles, user.roles)
				&& Objects.equals(StringUtils.trimToNull(created), StringUtils.trimToNull(user.created))
				&& Objects.equals(StringUtils.trimToNull(lastVisit), StringUtils.trimToNull(user.lastVisit));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, roles, created);
	}
}
