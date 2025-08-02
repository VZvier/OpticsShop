package com.vzv.shop.entity.product;

import com.vzv.shop.entity.Picture;
import jakarta.persistence.Column;
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
public class Liquid extends Product {

    @Column(name = "Volume")
    private String volume;

    @Column(name = "description")
    private String description;



    public Liquid(){
        super("liquid");
    }
    public Liquid(String id, String nomination, String brand, List<Picture> pictures, String price,
                  String volume, String description, boolean available) {
        super(id, "liquid", brand, pictures, price, available);
        this.volume = volume;
        this.description = description;
    }

    public Liquid(MultipartHttpServletRequest params){
        super(params.getParameter("id"),
                "liquid",
                params.getParameter("brand"),
                new LinkedList<>(),
                params.getParameter("price"),
                Boolean.parseBoolean( params.getParameter("available")));
        this.volume = params.getParameter("volume");
        this.description = params.getParameter("description");
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Liquid liquid = (Liquid) o;
        return getId() != null && Objects.equals(getId(), liquid.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
