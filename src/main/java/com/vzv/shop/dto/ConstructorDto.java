package com.vzv.shop.dto;

import com.vzv.shop.entity.product.Frame;
import com.vzv.shop.entity.product.Lens;
import com.vzv.shop.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "GLASSES_CONSTRUCTORS")
public class ConstructorDto {

    @Id
    private String id;
    @OneToOne
    private Product frame;
    @OneToOne
    private Product odLens;
    @OneToOne
    private Product osLens;
    private int distance;
    @Column(name = "Work_Price")
    private int workPrice;
    @Column(name = "Total_Price")
    private int totalPrice;


    public ConstructorDto(String id, Frame frame, Lens odLens, Lens osLens,
                          int distance, int workPrice, int totalPrice) {
        this.id = id;
        this.frame = frame;
        this.odLens = odLens;
        this.osLens = osLens;
        this.distance = distance;
        this.workPrice = workPrice;
        this.totalPrice = totalPrice;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ConstructorDto that = (ConstructorDto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
