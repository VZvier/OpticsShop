package com.vzv.shop.request;

import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConstructorRequest {

    private String frame;
    private String odLens;
    private String odAngle;
    private String osLens;
    private String osAngle;
    private String distance;
    private String workPrice;
    private String amount;
    private String total;

    public ConstructorRequest(String frame, String odLens, String odAngle, String osLens,
                              String osAngle, String distance, String workPrice,
                              String amount, String total) {
        this.frame = frame;
        this.odLens = odLens;
        this.odAngle = odAngle;
        this.osLens = osLens;
        this.osAngle = osAngle;
        this.distance = distance;
        this.workPrice = workPrice;
        this.amount = amount;
        this.total = total;
    }

    public GlassesConstructor toConstructor(List<ProductDTO> goods){
        ProductDTO frame = goods.stream().filter(p -> p.getId().contains(this.frame)).toList().get(0);
        ProductDTO odLens = goods.stream().filter(p -> p.getId().contains(this.odLens)).toList().get(0);
        ProductDTO osLens = goods.stream().filter(p -> p.getId().contains(this.osLens)).toList().get(0);
        return new GlassesConstructor(RandomStringUtils.randomAlphanumeric(12),
                frame, odLens, this.odAngle, osLens, this.osAngle,
                this.distance, this.workPrice, this.amount, this.total
        );
    }

    @Override
    public String toString() {
        return "\nConstructorRequest{" +
                "frame='" + frame + '\'' +
                ", odLens='" + odLens + '\'' +
                ", odAngle='" + odAngle + '\'' +
                ", osLens='" + osLens + '\'' +
                ", osAngle='" + osAngle + '\'' +
                ", distance='" + distance + '\'' +
                ", workPrice='" + workPrice + '\'' +
                ", amount='" + amount + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
