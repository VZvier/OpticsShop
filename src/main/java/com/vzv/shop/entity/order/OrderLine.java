package com.vzv.shop.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.data.TestData;
import com.vzv.shop.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Slf4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ORDER_LINES")
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne(targetEntity = ProductDTO.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "Product_Id", referencedColumnName = "Id")
	private ProductDTO product;
	private String quantity;
	private String price;


	public OrderLine(String id, ProductDTO product, String quantity, String price) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.price = TestData.formatPrice(price);
	}

	public OrderLine(ProductDTO product, String quantity){
		this.product = product;
		this.quantity = quantity;
	}

	@JsonIgnore
	public String getTotal(){
		String price = this.price.replaceAll("\\D+", "");
		String quantity = this.quantity.replaceAll("\\D+", "");
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.parseInt(price) * Integer.parseInt(quantity));
		sb.insert(sb.length() - 2, ",");
		return sb.toString();
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		OrderLine orderLine = (OrderLine) o;
		return getId() != null && Objects.equals(getId(), orderLine.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}