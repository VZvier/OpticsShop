package com.vzv.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PICTURES")
public class PictureDto {

    @Id
    private String id;

    @ManyToMany(mappedBy = "pictures", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Product> products;
    private String type;
    private String name;
    private byte[] content;

    public String useBase64Encoder(){
        return Base64.getEncoder().encodeToString(this.content);
    }

    public PictureDto(String id,
                      List<Product> products,
                      String type,
                      String name,
                      byte[] content) {
        this.id = id.trim();
        this.products = products;
        this.type = type.trim();
        this.name = name.trim();
        this.content = content;
    }

    public Picture toPicture(){
        return new Picture(id, type, name, content);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PictureDto that = (PictureDto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
