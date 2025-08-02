package com.vzv.shop.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDRESSES")
public class Address {

    @Id
    private String id;
    private String deliveryService;
    private String deliveryType;
    private String country;
    private String region;
    private String city;
    private String settlement;
    private String street;
    private String apartment;
    private String deliveryDepartment;
    private String parcelTerminal;

    public Address(String id, MultipartHttpServletRequest request) {
        this.id = id;
        this.deliveryService = request.getParameter("deliveryService");
        this.deliveryType = request.getParameter("deliveryType");
        this.country = request.getParameter("country");
        this.region = request.getParameter("region");
        this.city = request.getParameter("city");
        this.settlement = request.getParameter("settlement");
        this.street = request.getParameter("street");
        this.apartment = request.getParameter("apartment");
        this.deliveryDepartment = request.getParameter("deliveryDepartment");
        this.parcelTerminal = request.getParameter("parcelTerminal");
    }

    public String toInlineOrEmpty() {
        if (this.deliveryType == null) {
            return "";
        } else {
            return "Сервис: " + this.deliveryService.strip() + ", Доставка: " + this.deliveryType.strip() + ". Адресс: "
                    + this.country.strip() + ", " + this.region.strip() + ", " + this.city.strip() + ", "
                    + (this.settlement != null && !this.settlement.contains("null") ? this.settlement.strip() + ", " : "")
                    + (this.street != null && !this.street.contains("null") ? this.street.strip() + ", " : "")
                    + (this.apartment != null && !this.apartment.contains("null") ? this.apartment.strip() : "")
                    + (this.deliveryDepartment != null && !this.deliveryDepartment.contains("null") ? this.deliveryDepartment.strip() : "")
                    + (this.parcelTerminal != null && !this.parcelTerminal.contains("null") ? this.parcelTerminal.strip() : "");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(id, address.id) && Objects.equals(deliveryService, address.deliveryService)
                && Objects.equals(deliveryType, address.deliveryType) && Objects.equals(country, address.country)
                && Objects.equals(region, address.region) && Objects.equals(city, address.city)
                && Objects.equals(settlement, address.settlement) && Objects.equals(street, address.street)
                && Objects.equals(apartment, address.apartment)
                && Objects.equals(deliveryDepartment, address.deliveryDepartment)
                && Objects.equals(parcelTerminal, address.parcelTerminal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryService, deliveryType, country, region, city, settlement, street,
                apartment, deliveryDepartment, parcelTerminal);
    }
}
