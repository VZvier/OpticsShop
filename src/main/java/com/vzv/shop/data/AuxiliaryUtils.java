package com.vzv.shop.data;

import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import com.vzv.shop.entity.order.OrderLine;
import com.vzv.shop.request.ConstructorRequest;
import com.vzv.shop.request.OrderLineRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuxiliaryUtils {

    public static GlassesConstructor makeConstructorOrderLine(ConstructorRequest cOLRequest, List<ProductDTO> goods){
        List<ProductDTO> products = findProducts(cOLRequest, goods);
        return new GlassesConstructor(RandomStringUtils.randomAlphanumeric(12),
                products.get(0), products.get(1), cOLRequest.getOdAngle(), products.get(2), cOLRequest.getOsAngle(),
                cOLRequest.getDistance(), cOLRequest.getWorkPrice(), cOLRequest.getAmount(), cOLRequest.getTotal());
    }

    public static List<GlassesConstructor> makeConstructorOrderLines(List<ConstructorRequest> cOLRequests, List<ProductDTO> goods){
        List<GlassesConstructor> result = new ArrayList<>();
        for(ConstructorRequest cOLR : cOLRequests){
            List<ProductDTO> products = findProducts(cOLR, goods);
            result.add(new GlassesConstructor(RandomStringUtils.randomAlphanumeric(12),
                    products.get(0), products.get(1), cOLR.getOdAngle(), products.get(2), cOLR.getOsAngle(),
                    cOLR.getDistance(), cOLR.getWorkPrice(), cOLR.getAmount(), cOLR.getTotal()));
        }
        return result;
    }

    public static List<OrderLine> makeOrderLines(List<OrderLineRequest> oLRequests, List<ProductDTO> products){
        List<OrderLine> result = new ArrayList<>();
        for (OrderLineRequest olr : oLRequests){
            ProductDTO olProduct = products.stream().filter(p -> p.getId().equals(olr.getProductId())).toList().get(0);
            result.add(new OrderLine(olProduct, String.valueOf(olr.getAmount())));
        }
        return result;
    }

    public static OrderLine makeOrderLine(OrderLineRequest oLRequest, List<ProductDTO> products){
        ProductDTO product = products.stream().filter(p -> p.getId().equals(oLRequest.getProductId())).toList().get(0);
        return new OrderLine(product, String.valueOf(oLRequest.getAmount()));
    }

    public static String makeRandomString(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }

    private static List<ProductDTO> findProducts(ConstructorRequest cOLRequest, List<ProductDTO> goods){
        return List.of(goods.stream().filter(p -> p.getId().contains(cOLRequest.getFrame())).toList().get(0),
                goods.stream().filter(p -> p.getId().contains(cOLRequest.getOdLens())).toList().get(0),
                goods.stream().filter(p -> p.getId().contains(cOLRequest.getOsLens())).toList().get(0));
    }

    public static  <T> T copy(T source, T target, String... props){
        BeanUtils.copyProperties(source, target, props);
        return target;
    }
}