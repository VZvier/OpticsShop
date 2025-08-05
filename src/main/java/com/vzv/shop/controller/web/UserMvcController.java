package com.vzv.shop.controller.web;

import com.vzv.shop.entity.user.Address;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/shop/api/users")
public class UserMvcController {

    private final UserService userService;

    public UserMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("page/{id}")
    public String getUserInfoPage(@PathVariable("id") String customerId, Model model) {
        log.info("GetUserInfoPage()");
        try {
            Customer customer = userService.getCustomerById(customerId);
            model.addAttribute("customer", customer);
        } catch (Exception e) {
            log.error("Customer id: \"{}\" not found!", customerId);
        }
        return "pages/staff/user-info";
    }

    @GetMapping("new")
    public String getCreateUserPage() {
        return "pages/staff/create-user";
    }


    @GetMapping("/customers/all")
    public String getAll(Model model) {
        model.addAttribute("customerList", userService.getAllCustomers());
        return "pages/staff/users";
    }

    @GetMapping("address/page")
    public String getAddressPage(Authentication auth, Model model) {
        String id;
        if (auth != null) {
            id = userService.getByLogin(auth.getName()).getId();
        } else {
            return "pages/login";
        }
        model.addAttribute("customer", userService.getCustomerById(id));
        return "pages/delivery-address";
    }

    @GetMapping("renew/customer/{id}")
    public String openUpdatePage(@PathVariable("id") String id, Model model) {
        Customer customer = userService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "pages/staff/user-info";
    }

    @PutMapping(value = "address/renew/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveUpdateAddress(@PathVariable("userId") String id,
                                    MultipartHttpServletRequest request, Model model) {
        Address address = new Address(id, request);
        log.info("New Address(id, request): {}", address);
        Customer customer = userService.getCustomerById(id);
        customer.setAddress(address);
        userService.saveCustomer(customer);
        model.addAttribute("customer", userService.getCustomerById(id));
        return "pages/basket-page";
    }
}
