package com.vzv.shop.entity.order;

import com.vzv.shop.data.TestData;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "CONSTRUCTORS_ORDER_LINES")
public class ConstructorOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "Order_Id")
    private String orderId;

    @ManyToOne(targetEntity = GlassesConstructor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "Constructor_Id", referencedColumnName = "Id")
    private GlassesConstructor constructor;
    private String quantity;
    @Column(name = "Price")
    private String price;

    public ConstructorOrderLine(String orderId, GlassesConstructor constructor,
                                String quantity, String price){
        this.orderId = orderId;
        this.constructor = constructor;
        this.quantity = quantity;
        this.price = TestData.formatPrice(price);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ConstructorOrderLine that = (ConstructorOrderLine) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
