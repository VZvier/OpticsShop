package com.vzv.shop.entity.sattlemants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "STREETS")
public class Street {

    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "City_Id")
    private String cityId;
    @Column(name = "Name_Ru")
    private String nameRu;
    @Column(name = "Name_En")
    private String nameEn;
    @Column(name = "Name_Ua")
    private String nameUa;

    public Street(String id, String cityId, String nameEn, String nameRu, String nameUa) {
        this.id = id.trim();
        this.cityId = cityId.trim();
        this.nameEn = nameEn.trim();
        this.nameRu = nameRu.trim();
        this.nameUa = nameUa.trim();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Street street = (Street) o;
        return getId() != null && Objects.equals(getId(), street.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
