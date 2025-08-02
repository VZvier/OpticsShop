package com.vzv.shop.controller.web;

import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.service.PictureService;
import com.vzv.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop/products")
public class ProductMvcController {

    private final ProductService productService;
    private final PictureService picService;

    public ProductMvcController(ProductService productService, PictureService picService) {
        this.productService = productService;
        this.picService = picService;
    }

    @GetMapping("/page")
    public String getAll(Model model) {
        List<ProductDTO> products = productService.getAll().stream()
                .filter(p -> !p.getNomination().equals("glasses-constructor")).toList();
        model.addAttribute("products", products);
        return "pages/main-page";
    }

    @GetMapping("details/{id}")
    public String getGoodsDetailsByProductId(@PathVariable("id") String id, Model model) {
        ProductDTO productDTO = productService.getDtoById(id);
        if (productDTO != null && (productDTO.getPictures() == null || productDTO.getPictures().isEmpty())) {
            productDTO.setPictures(picService.getByProductId(id));
        }
        model.addAttribute("product", productDTO);
        return "pages/product-page";
    }

    @GetMapping("/new")
    public String getProductCreationPage() {
        return "pages/staff/product-creation";
    }

    @GetMapping("/renew/{productId}")
    public String getUpdatePage(@PathVariable("productId") String prodId, Model model) {
        model.addAttribute("product", productService.getDtoById(prodId));
        return "pages/staff/product-renovation";
    }

    @DeleteMapping("/rm/{id}")
    public String removeProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/shop/products/all";
    }

}
