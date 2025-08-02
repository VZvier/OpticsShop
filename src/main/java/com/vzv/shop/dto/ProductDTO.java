package com.vzv.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.data.TestData;
import com.vzv.shop.data.Translate;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "GOODS")
public class ProductDTO {

    @Id
    private String id;
    private String brand;
    private String nomination;

    @OneToMany(targetEntity = PictureDto.class, fetch = FetchType.EAGER)
    @JoinTable(name = "GOODS_PICTURES",
            joinColumns = {@JoinColumn(name = "Product_ID")},
            inverseJoinColumns = {@JoinColumn(name = "Picture_Id")})
    @ToString.Exclude
    private List<PictureDto> pictures = new LinkedList<>();
    private String model;
    @Column(name = "Frame_Type")
    private String frameType;
    @Column(name = "Size")
    private String frameSize;
    private String gender;
    @Column(name = "Lens_Type")
    private String lensType;
    private String country;
    private String coefficient;
    @Column(name = "Sphere")
    private String sp;
    @Column(name = "Cylinder")
    private String cyl;
    private String distance;
    private String volume;
    private String description;
    private String price;
    private boolean available;

    public ProductDTO(String id, String brand, String nomination, List<PictureDto> pictures, String model,
                      String frameType, String frameSize, String gender, String lensType, String country,
                      String coefficient, String sp, String cyl, String distance, String volume,
                      String description, String price, boolean available) {
        this.id = id.trim();
        this.brand = brand.trim();
        this.nomination = nomination.trim();
        this.pictures = pictures;
        this.model = model!= null ? model.trim() : model;
        this.frameType = frameType != null ? frameType.trim() : frameType;
        this.frameSize = frameSize != null ? frameSize.trim() : frameSize;
        this.gender = gender != null ? gender.trim() : gender;
        this.lensType = lensType != null ? lensType.trim() : lensType;
        this.country = country != null ? country.trim() : country;
        this.coefficient = coefficient != null ? coefficient.trim() : coefficient;
        this.sp = sp;
        this.cyl = cyl;
        this.distance = distance;
        this.volume = volume;
        this.description = description != null ? description.trim() : description;
        this.price = TestData.formatPrice(price);
        this.available = available;
    }


    @JsonIgnore
    public String getNominationRu() {
        return this.getNomination() != null ? Translate.getRu(this.getNomination().trim()) : "";
    }

    @JsonIgnore
    public String getFrameTypeRu() {
        return this.getFrameType() != null ? Translate.getRu(this.getFrameType().trim()) : "";
    }

    @JsonIgnore
    public String getFrameSizeRu() {
        return this.getFrameSize() != null ? Translate.getRu(this.getFrameSize().trim()) : "";
    }

    @JsonIgnore
    public String getGenderRu() {
        return this.getGender() != null ? Translate.getRu(this.getGender().trim()) : "";
    }

    @JsonIgnore
    public String getLensTypeRu() {
        return this.getLensType() != null ? Translate.getRu(this.getLensType().trim()) : "";
    }

    @JsonIgnore
    public String getCountryRu() {
        return this.getCountry() != null ? Translate.getRu(this.getCountry().trim()) : "";
    }

    @JsonIgnore
    public String getAvailableRu() {
        return Translate.getRu(String.valueOf(this.isAvailable()));
    }

    private String checkPrice(String price){
        if (!price.contains(",") && !price.contains(".")){
            price = price + ",00";
        } else {
            price = price.replaceAll("\\D+", "");
            int i = price.length() - 2;
            price = price.substring(0, i) + "," + price.substring(i);
        }
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO that)) return false;
        return available == that.available
                && Objects.equals(StringUtils.trimToNull(id), StringUtils.trimToNull(that.id))
                && Objects.equals(StringUtils.trimToNull(brand), StringUtils.trimToNull(that.brand))
                && Objects.equals(StringUtils.trimToNull(nomination), StringUtils.trimToNull(that.nomination))
                && Objects.equals(StringUtils.trimToNull(model), StringUtils.trimToNull(that.model))
                && Objects.equals(StringUtils.trimToNull(frameType), StringUtils.trimToNull(that.frameType))
                && Objects.equals(StringUtils.trimToNull(frameSize), StringUtils.trimToNull(that.frameSize))
                && Objects.equals(StringUtils.trimToNull(gender), StringUtils.trimToNull(that.gender))
                && Objects.equals(StringUtils.trimToNull(lensType), StringUtils.trimToNull(that.lensType))
                && Objects.equals(StringUtils.trimToNull(country), StringUtils.trimToNull(that.country))
                && Objects.equals(StringUtils.trimToNull(coefficient), StringUtils.trimToNull(that.coefficient))
                && Objects.equals(StringUtils.trimToNull(sp), StringUtils.trimToNull(that.sp))
                && Objects.equals(StringUtils.trimToNull(cyl), StringUtils.trimToNull(that.cyl))
                && Objects.equals(StringUtils.trimToNull(distance), StringUtils.trimToNull(that.distance))
                && Objects.equals(StringUtils.trimToNull(volume), StringUtils.trimToNull(that.volume))
                && Objects.equals(StringUtils.trimToNull(description), StringUtils.trimToNull(that.description))
                && Objects.equals(StringUtils.trimToNull(price), StringUtils.trimToNull(that.price));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, nomination, pictures, model, frameType, frameSize, gender, lensType,
                country, coefficient, sp, cyl, distance, volume, description, price, available);
    }
}
