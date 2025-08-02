package com.vzv.shop.entity.product;

import com.vzv.shop.entity.Picture;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
public class Other extends Product{

    public Other(String id, String nomination, String brand, List<Picture> pictures, String price, boolean available) {
        super(id, nomination, brand, pictures, price, available);
    }

    public Other(){
        super("other");
    }

    public Other(MultipartHttpServletRequest params){
        super(params.getParameter("id"),
                params.getParameter("nomination"),
                params.getParameter("brand"),
                new LinkedList<>(),
                params.getParameter("price"),
                Boolean.parseBoolean( params.getParameter("available")));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Other other = (Other) o;
        return getId() != null && Objects.equals(getId(), other.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}