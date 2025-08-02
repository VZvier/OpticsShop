package com.vzv.shop.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.data.Translate;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.enumerated.FrameSize;
import com.vzv.shop.enumerated.FrameType;
import com.vzv.shop.enumerated.Gender;
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
public class Frame extends Product {

    private String model;

    @Column(name = "Frame_Type")
    private String frameType;

    @Column(name = "Size")
    private String frameSize;

    @Column(name = "Gender")
    private String gender;

    public Frame(String id, String nomination, String brand, List<Picture> pictures, String price, String model,
                 FrameType frameType, FrameSize frameSize, Gender gender, boolean available) {
        super(id, "frame", brand, pictures, price, available);
        this.model = model;
        this.frameType = frameType.getLabel();
        this.frameSize = frameSize.getLabel();
        this.gender = gender.getLabel();
    }

    public Frame(String id, String brand, List<Picture> pictures, String price, String model,
                 FrameType frameType, FrameSize frameSize, Gender gender, boolean available) {
        super(id, "frame", brand, pictures, price, available);
        this.model = model;
        this.frameType = frameType.getLabel();
        this.frameSize = frameSize.getLabel();
        this.gender = gender.getLabel();
    }

    public Frame(){
        super("frame");
    }

    public Frame(MultipartHttpServletRequest params){
        super(params.getParameter("id"),
                "frame",
                params.getParameter("brand"),
                new LinkedList<>(),
                params.getParameter("price"),
                Boolean.parseBoolean( params.getParameter("available")));
        this.model = params.getParameter("model");
        this.frameType = FrameType.getByLabel(params.getParameter("frameType")).getLabel();
        this.frameSize = FrameSize.getByLabel(params.getParameter("frameSize")).getLabel();
        this.gender = Gender.getByLabel(params.getParameter("gender")).getLabel();
    }

    @JsonIgnore
    public String getFSizeRu() {
        return Translate.getRu( this.frameSize );
    }

    @JsonIgnore
    public String getFTypeRu(){
        return Translate.getRu( this.getFrameType() );
    }

    @JsonIgnore
    public String getGenderRu(){
        return Translate.getRu( this.getGender() );
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Frame frame = (Frame) o;
        return getId() != null && Objects.equals(getId(), frame.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
