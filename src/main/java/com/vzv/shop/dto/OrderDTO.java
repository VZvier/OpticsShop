package com.vzv.shop.dto;

import com.vzv.shop.entity.user.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ORDERS")
public class OrderDTO {


    @Id
    private String id;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "Id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Order_Id")
    private List<OrderLineDTO> orderLines;

    private String address;
    private String created;
    private String updated;
    private String status;

    private transient List<ProductDTO> ordered = new ArrayList<>();


    public OrderDTO(String id, Customer customer, List<OrderLineDTO> orderLines,
                    String address, String created, String updated, String status) {
        this.id = id;
        this.customer = customer;
        this.orderLines = orderLines;
        this.address = address;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.ordered = fillOrdered();
    }

    public List<ProductDTO> fillOrdered(){
        return orderLines.stream().map(OrderLineDTO::getProduct).toList();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return getId() != null && Objects.equals(getId(), orderDTO.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
