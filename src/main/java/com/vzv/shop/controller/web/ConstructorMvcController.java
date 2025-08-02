package com.vzv.shop.controller.web;

import com.vzv.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop/api")
public class ConstructorMvcController {

    private final ProductService productService;

    public ConstructorMvcController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("constructor")
    public String getConstructor(Model model){
        model.addAttribute("products", productService.getAll().stream()
                .filter(p -> List.of("frame", "lens").contains(p.getNomination().trim()))
                .toList());
        return "pages/glasses-constructor";
    }
}
