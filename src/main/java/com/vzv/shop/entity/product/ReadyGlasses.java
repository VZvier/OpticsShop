package com.vzv.shop.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.data.Translate;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.enumerated.FrameSize;
import com.vzv.shop.enumerated.FrameType;
import com.vzv.shop.enumerated.Gender;
import com.vzv.shop.enumerated.LensType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
public class ReadyGlasses extends Product {

    private String model;

    @Column(name = "Frame_Type")
    private String frameType;

    @Column(name = "Size")
    private String frameSize;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Lens_Type")
    private String lensType;
    @Column(name = "Sphere")
    private String sp;
    private String distance;


    public ReadyGlasses(){
        super("ready-glasses");
    }

    public ReadyGlasses(String id, String nomination, String brand, List<Picture> pictures, String price,
                        String model, FrameType frameType, FrameSize frameSize, Gender gender, LensType lensType,
                        String sp, String distance, boolean available) {
        super(id, "ready-glasses", brand, pictures, price, available);
        this.model = model;
        this.frameType = frameType.getLabel();
        this.frameSize = frameSize.getLabel();
        this.gender = gender.getLabel();
        this.lensType = lensType.getLabel();
        this.sp = checkSpOrCyl(sp);
        this.distance = distance;
    }

    public ReadyGlasses(MultipartHttpServletRequest params){
        super(params.getParameter("id"),
                "ready-glasses",
                params.getParameter("brand"),
                new LinkedList<>(),
                params.getParameter("price"),
                Boolean.parseBoolean( params.getParameter("available")));
        this.model = params.getParameter("model");
        this.frameType = FrameType.getByLabel(params.getParameter("frameType")).getLabel();
        this.frameSize = FrameSize.getByLabel(params.getParameter("frameSize")).getLabel();
        this.gender = Gender.getByLabel(params.getParameter("gender")).getLabel();
        this.lensType = LensType.getByLabel(params.getParameter("lensType")).getLabel();
        this.sp = checkSpOrCyl(params.getParameter("sp"));
        this.distance = params.getParameter("distance");
    }

    @JsonIgnore
    public String getFrameTypeRu(){
        return Translate.getRu( this.getFrameType() );
    }

    @JsonIgnore
    public String getFSizeRu() {
        return Translate.getRu( this.frameSize );
    }

    @JsonIgnore
    public String getGenderRu(){
        return Translate.getRu( this.getGender() );
    }

    @JsonIgnore
    public String getLensTypeRu(){
        return Translate.getRu( this.getLensType() );
    }

    @JsonIgnore
    public String getSpStr() {
        return this.sp != null ? transformStr(String.valueOf(this.sp)) : "";
    }

    private String transformStr(String str) {
        if (str.charAt(0) == '0') {
            return "0";
        }
        String mark = (str.charAt(0) == '-') ? "-" : "+";
        str = (str.charAt(0) == '-') ? str.substring(1) : str;
        str = str.length() > 2
                ? str.substring(0, str.length() - 2) + "," + str.substring(str.length() - 2)
                : "0," + str;
        return mark + str;
    }


    public String checkSpOrCyl(String value){
        if (value.charAt(0) == '0' && value.length() == 1) {
            return "0";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(value.startsWith("-") ? "-" : "+");
            value = value.startsWith("-") || value.startsWith("+") ? value.substring(1) : value;
            String separator = ",";
            String[] split = value.split((value.contains(".") ? "\\." : (value.contains(",") ? "," : "")));
            sb.append(split[0])
                    .append(separator)
                    .append(split.length > 1 ? split[1] : ",00")
                    .append(split.length > 1 && split[1].length() == 1 ? "0" : "");
            return sb.toString();
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ReadyGlasses that = (ReadyGlasses) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}