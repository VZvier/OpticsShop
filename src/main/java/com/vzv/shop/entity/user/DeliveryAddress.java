package com.vzv.shop.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DELIVERY_ADDRESSES")
public class DeliveryAddress {

    @Id
    private String id;
    private String country;
    private String region;
    private String category;
    private String community;
    private String settlement;
    @Column(name = "Delivery_Service")
    private String service;
    private String department;


    public DeliveryAddress(String id, String country, String region, String category,
                           String community, String settlement, String service, String department) {
        this.id = id.trim();
        this.country = country.trim();
        this.region = region.trim();
        this.category = category.trim();
        this.community = community.trim();
        this.settlement = settlement.trim();
        this.service = service.trim();
        this.department = department.trim();
    }

}