package com.vzv.shop.entity.product;

import com.vzv.shop.entity.Picture;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
public class MedicalEquipment extends Product {

    @Column(name = "Model")
    private String model;

    @Column(name = "Description")
    private String description;


    public MedicalEquipment(String id, String nomination, String brand, List<Picture> pictures, String price,
                            String model, String description, boolean available) {
        super(id, "medical equipment", brand, pictures, price, available);
        this.model = model;
        this.description = description;
    }

    public MedicalEquipment(){
        super("medical equipment");
    }

    public MedicalEquipment(MultipartHttpServletRequest params){
        super(params.getParameter("id"),
                "medical equipment",
                params.getParameter("brand"),
                null,
                params.getParameter("price"),
                Boolean.parseBoolean( params.getParameter("available")));
        this.model = params.getParameter("model");
        this.description = params.getParameter("description");
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MedicalEquipment that = (MedicalEquipment) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
