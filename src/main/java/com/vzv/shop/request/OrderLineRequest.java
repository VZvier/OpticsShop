package com.vzv.shop.request;

import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.order.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRequest {

    private String productId;
    private int amount;

    public OrderLine toOrderLine(){
        ProductDTO product = new ProductDTO();
        product.setId(this.productId);
        return new OrderLine(product, String.valueOf(this.amount));
    }

    public OrderLine toOrderLine(ProductDTO productDto){
        ProductDTO product = new ProductDTO();
        product.setId(this.productId);
        return new OrderLine(productDto, String.valueOf(this.amount));
    }

    @Override
    public String toString() {
        return "OrderLineRequest{" +
                "productId='" + productId + "'" +
                ", amount=" + amount + "}";
    }
}
