package com.vzv.shop.controller.web;

import com.vzv.shop.entity.order.Order;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.ProductService;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/shop/orders")
public class OrderMvcController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public OrderMvcController(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }


    @GetMapping({"page/{id}", "page/"})
    public String getOrderPage(@Nullable @PathVariable("id") String id, Model model){
        model.addAttribute("orderList", orderService.getById(id));
        return "pages/staff/orders";
    }

    @GetMapping("customer/{id}")
    public String getCustomerOfOrderPage(@PathVariable("id") String id, Model model){
        log.info("getCustomerOfOrderPage({})", id);
        model.addAttribute("customer", orderService.getCustomerOfOrder(id));
        return "pages/staff/user-info";
    }

    @GetMapping("basket/page")
    public String getBasketPage() {
        return "pages/basket-page";
    }

    @GetMapping({"all", "by-user-id/{userId}", "by_id/{orderId}", "by_date_of_{dateOf}/{date}"})
    public String getOrdersPage(@Nullable @PathVariable("userId") String userId,
                                @Nullable @PathVariable("orderId") String orderId,
                                @Nullable @PathVariable("date") String date,
                                @Nullable @PathVariable("dateOf") String dateOf,
                                Model model) {
        List<Order> orders = new ArrayList<>();
        if (!StringUtils.isBlank(userId)){
            log.info("UserId !blank! Is: {}, oID trimToEmpty: {}", userId, StringUtils.trimToEmpty(orderId));
            orders = orderService.getCustomersOrders(userId);
        } else if (!StringUtils.isBlank(orderId)){
            orders = List.of(orderService.getById(StringUtils.trimToNull(orderId)));
        } else {
            if (dateOf != null) {
                switch (dateOf) {
                    case ("creation") -> {
                        log.info("Get orders created at {}!", date);
                        orders = orderService.getByCreated(date);
                    }
                    case ("updating") -> {
                        log.info("Get orders updated at {}!", date);
                        orders = orderService.getByUpdated(date);
                    }
                    default -> {
                        log.info("Get orders created today");
                        orders = orderService.getByCreated(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()));
                    }
                }
            } else {
                orders = orderService.getAll();
            }
        }
        model.addAttribute("orderList", orders.size() > 0 ? orders : new ArrayList<>());
        List<String> ids = orders.stream()
                .map(Order::getOrderLines)
                .flatMap(Collection::stream)
                .map(ol -> ol.getProduct().getId().strip())
                .toList();
        model.addAttribute("goods", ids.size() > 0 ? productService.getAllByIds(ids) : new ArrayList<>());
        return "pages/staff/orders";
    }

    @GetMapping("per/{period}")
    public String getOrdersPerMonthOrYear(@PathVariable("period") String period, Model model){
        List<Order> orders = orderService.getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate today = LocalDate.now();
        LocalDate startingPoint;

        switch (period){
            case ("month") -> {
                startingPoint = today.minusMonths(1);
            }
            case ("year") -> {
                startingPoint = today.minusYears(1);
            }
            default -> {
                startingPoint = LocalDate.parse("01.01." + today.getYear(), formatter);
            }
        }

        orders =  orders.stream()
                .filter(o -> LocalDate.parse(o.getCreated().trim(), formatter).isAfter(startingPoint))
                .toList();

        model.addAttribute("orderList", orders);
        return "pages/staff/orders";
    }

    @GetMapping("unchecked")
    public String getUncheckedOrders(Model model){
        model.addAttribute("orderList", orderService.getUnchecked());
        return "pages/staff/orders";
    }

    @GetMapping("my-orders")
    public String getCustomersOrders(Authentication auth, Model model){
        if (!auth.isAuthenticated()){
            log.info("Not authenticated!");
            return "pages/login";
        } else {
            List<Order> customerOrders = orderService.getCustomersOrders(userService.getByLogin(auth.getName()).getId());
            model.addAttribute("orderList", customerOrders);
            log.info("Got orders {}!", customerOrders.size());
            return "pages/staff/orders";
        }
    }

    @GetMapping("new")
    public String getCreateOrderPage(){
        return "pages/staff/order_create";
    }

    @GetMapping("renew/{id}")
    public String updatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute("order", orderService.getById(id.strip()));
        return "pages/staff/order_update_page";
    }

    @GetMapping("saved-order/{id}")
    public String savedOrder(@PathVariable("id") String id, Model model) {
        model.addAttribute("order", orderService.getById(id.trim()));
        return "pages/orders";
    }
}
