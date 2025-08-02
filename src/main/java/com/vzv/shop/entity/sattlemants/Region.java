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
@Table(name = "REGIONS")
public class Region {

    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Name_Ru")
    private String nameRu;
    @Column(name = "Name_En")
    private String nameEn;
    @Column(name = "Name_Ua")
    private String nameUA;

    public Region(String id, String nameRu, String nameEn, String nameUA) {
        this.id = id.trim();
        this.nameRu = nameRu.trim();
        this.nameEn = nameEn.trim();
        this.nameUA = nameUA.trim();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Region region = (Region) o;
        return getId() != null && Objects.equals(getId(), region.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
