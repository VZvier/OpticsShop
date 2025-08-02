package com.vzv.shop.entity.user;

import com.vzv.shop.enumerated.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USRS")
public class FullUser {

	@Id
	private String id;
	private String login;
	private String password;
	
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "ROLES",
            joinColumns = @JoinColumn(name = "User_Id", referencedColumnName = "Id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
	@Fetch(FetchMode.SELECT)
	@BatchSize(size = 500)
	private Set<Role> roles;
	
	@OneToOne
	@JoinColumn(name = "Id", referencedColumnName = "User_Id")
	private Customer customer;
	
	public FullUser(String id){
		this.id = id;
	}

	public FullUser(String id, MultipartHttpServletRequest request){
		this.id = request.getParameter("id") != null ? request.getParameter("id") : id;
		this.login = request.getParameter("login");
		this.password = request.getParameter("pass");
		this.roles = Collections.singleton(Role.getByLabel(request.getParameter("roles")));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FullUser fullUser)) return false;
		return Objects.equals(id, fullUser.id) && Objects.equals(login, fullUser.login)
				&& Objects.equals(password, fullUser.password) && Objects.equals(roles, fullUser.roles)
				&& Objects.equals(customer, fullUser.customer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, roles, customer);
	}
}