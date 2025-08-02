package com.vzv.shop.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vzv.shop.data.Translate;
import com.vzv.shop.entity.Picture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "GOODS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString
public class Product {

    @Id
    private String id;
    private String nomination;
    private String brand;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "GOODS_PICTURES",
            joinColumns = {@JoinColumn(name = "Product_Id")},
            inverseJoinColumns = {@JoinColumn(name = "Picture_Id")})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Picture> pictures;
    private String price;
    private boolean available;

    public Product(String nomination) {
        this.nomination = nomination;
    }

    public Product(MultipartHttpServletRequest params) {
        this.id = params.getParameter("id").trim();
        this.nomination = params.getParameter("nomination").trim();
        this.brand = params.getParameter("brand").trim();
        this.pictures = new LinkedList<>();
        this.price = checkPrice(params.getParameter("price").trim());
        this.available = Boolean.parseBoolean(params.getParameter("available").trim());
    }

    public Product(String id, String nomination, String brand, List<Picture> pictures, String price, boolean available) {
        this.id = id;
        this.nomination = nomination;
        this.brand = brand;
        this.pictures = pictures;
        this.price = checkPrice(price);
        this.available = available;
    }

    public Product(String id, String nomination, String price) {
        this.id = id.trim();
        this.nomination = nomination.trim();
        this.price = checkPrice(price);
    }


    @JsonIgnore
    public String getNominationRu() {
        return Translate.getRu(this.getNomination());
    }

    @JsonIgnore
    public String getAvailableStrRu() {
        return Translate.getRu(String.valueOf(this.isAvailable()));
    }

    @JsonIgnore
    public String getPriceStr() {
        String str = this.price != null ? String.valueOf(this.price) : "";
        if (this.price != null && str.length() > 2) {
            return str.substring(0, str.length() - 2) + "," + str.substring(str.length() - 2);
        } else {
            return "";
        }
    }

    public void setPictures(List<Picture> pictures) {
        if (this.pictures != null && !this.pictures.isEmpty()) {
            this.pictures.addAll(pictures);
        } else {
            this.pictures = pictures;
        }
    }

    private int toIntegerPrice() {
        String price = StringUtils.getDigits(this.price);
        return Integer.parseInt(price);
    }

    private String checkPrice(String price) {
        if (!price.contains(",") && !price.contains(".")) {
            price = price + ",00";
        } else {
            price = price.replaceAll("\\D+", "");
            int i = price.length() - 2;
            price = price.substring(0, i) + "," + price.substring(i);
        }
        return price;
    }

    public void changePriceByPercent(String action, Integer percent) {
        int price = toIntegerPrice();
        int changedPrice = 0;
        switch (action){
            case ("increase") ->{
                changedPrice = price + price / 100 * percent;
            }
            case ("decrease") ->{
                changedPrice = price - price / 100 * percent;
            }
        }
        String strOfPrice = String.valueOf(changedPrice);
        this.price = StringUtils.overlay(strOfPrice, ",",
                strOfPrice.length() - 2, strOfPrice.length() - 2);
    }

    public void changePriceByValue(String action, Integer value) {
        int price = toIntegerPrice();
        switch (action){
            case ("increase") ->{
                price += value * 100;
            }
            case ("decrease") ->{
                price -= value * 100;
            }
        }
        String strOfPrice = String.valueOf(price);
        this.price = StringUtils.overlay(strOfPrice, ",",
                strOfPrice.length() - 2, strOfPrice.length() - 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return available == product.available && Objects.equals(id, product.id)
                && Objects.equals(nomination, product.nomination) && Objects.equals(brand, product.brand)
                && Objects.equals(pictures, product.pictures) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomination, brand, pictures, price, available);
    }
}