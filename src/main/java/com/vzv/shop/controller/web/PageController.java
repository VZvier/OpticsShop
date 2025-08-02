package com.vzv.shop.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/shop/api/administration")
    public String getAdminPage(){
        return "pages/staff/users";
    }

}
