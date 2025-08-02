package com.vzv.shop.controller.web;

import com.vzv.shop.entity.user.FullUser;
import com.vzv.shop.request.LoginRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/api/users/")
public class AuthMvcController {


    @GetMapping("login")
    public String openLoginPage(Model model, Authentication auth) {
        if (auth != null) {
            return "redirect:/";
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "pages/login";
    }

    @GetMapping("registration")
    public String openRegistrationPage(Model model, Authentication auth) {
        if (auth != null) {
            return "redirect:/";
        }
        model.addAttribute("registration", new FullUser(""));
        return "pages/registration";
    }
}
