package com.vzv.shop.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.data.Translate;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.enumerated.Country;
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
public class Lens extends Product {


    @Column(name = "Lens_Type")
    private String lensType;

    @Column(name = "Country")
    private String country;

    @Column(name = "Coefficient")
    private String coefficient;

    @Column(name = "Sphere")
    private String sp;

    @Column(name = "Cylinder")
    private String cyl;

    public Lens(){
        super("lens");
    }

    public Lens(String id, String nomination, String brand, List<Picture> pictures,
                String price, LensType lensType, Country country,
                String coefficient, String sp, String cyl, boolean available) {
        super(id, "lens", brand, pictures, price, available);
        this.lensType = lensType.getLabel();
        this.country = country.getLabel();
        this.coefficient = coefficient;
        this.sp = checkSpOrCyl(sp);
        this.cyl = checkSpOrCyl(cyl);
    }

    public Lens(MultipartHttpServletRequest params) {
        super(params.getParameter("id"),
                "lens",
                params.getParameter("brand"),
                new LinkedList<>(),
                params.getParameter("price"),
                Boolean.parseBoolean(params.getParameter("available")));
        this.lensType = LensType.getByLabel(params.getParameter("lensType")).getLabel();
        this.country = params.getParameter("country");
        this.coefficient = params.getParameter("coefficient");
        this.sp = checkSpOrCyl(params.getParameter("sp"));
        this.cyl = checkSpOrCyl(params.getParameter("cyl"));
    }

    @JsonIgnore
    public String getCountryRu() {
        return Translate.getRu(this.country);
    }

    @JsonIgnore
    public String getLTypeRu() {
        return Translate.getRu(this.getLensType());
    }

    @JsonIgnore
    public String getSpStr() {
        return this.sp != null ? transformStr(String.valueOf(this.sp)) : "";
    }

    @JsonIgnore
    public String getCylStr() {
        return this.cyl != null ? transformStr(String.valueOf(this.cyl)) : "";
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
            String regex = value.contains(".") ? "\\." : (value.contains(",") ? "," : "");
            String[] split = value.split(regex);
            sb.append(split[0])
                    .append(separator)
                    .append(split[1] != null ? split[1] : ",00")
                    .append(split[1] != null && split[1].length() == 1 ? "0" : "");
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
        Lens lens = (Lens) o;
        return getId() != null && Objects.equals(getId(), lens.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
