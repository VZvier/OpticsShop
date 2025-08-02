package com.vzv.shop.controller.rest;

import com.vzv.shop.data.CSVFileReader;
import com.vzv.shop.data.TXTReader;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.ProductService;
import com.vzv.shop.service.SettlementService;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/shop/data")
public class SimpleDataController {

    private final SettlementService settlementService;
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    public SimpleDataController(SettlementService settlementService, UserService userService, OrderService orderService, ProductService productService) {
        this.settlementService = settlementService;
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/regions")
    public List<String> getRegions(){
        return settlementService.getRegions("Ru");
    }

    @GetMapping("countries")
    public Map<String, String> getEnRuCountryMap() {
        return CSVFileReader.COUNTRIES;
    }


    @GetMapping("nominations")
    public List<String> getNominations() {
        List<String> result = productService.getAllNominations();
        log.info("Got product nominations: {}", result);
        return result;
    }

    @GetMapping("brands")
    public List<String> getBrands() {
        List<String> result = productService.getAllBrands();
        log.info("Got product brands: {}", result);
        return result;
    }

    @GetMapping("brands/{nomination}")
    public List<String> getBrandsByNomination(@PathVariable("nomination") String nomination) {
        List<String> result = productService.getAll().stream()
                .filter(product -> product.getNomination().trim().equals(nomination))
                .map(p -> p.getBrand().strip()).collect(Collectors.toSet()).stream().toList();
        log.info("Got product brands: {}", result);
        return result;
    }

    @GetMapping("models/{brand}")
    public List<String> getModels(@PathVariable String brand) {
        List<String> result = productService.getBrandModels(brand);
        log.info("Got product models: {}", result);
        return result.stream().map(String::strip).toList();
    }

    @GetMapping("volumes/{brand}")
    public List<String> getAllLiquidVolumes(@PathVariable("brand") String brand){
        return productService.getLiquidVolumes(brand);
    }

    @GetMapping("str-list/{parameter}")
    public List<String> getStringByParameter(@PathVariable("parameter")String param){
        return productService.getStringsByParameter(param);
    }

    @GetMapping("int-list/{parameter}")
    public List<String> getIntsByParameter(@PathVariable("parameter")String param){
        return productService.getIntsByParameter(param, null);
    }

    @GetMapping("/login/{login}")
    public boolean isLoginFree(@PathVariable("login") String login){
        return !userService.isLoginExists(login);
    }

    @GetMapping("/email/{email}")
    public boolean isEmailFree(@PathVariable("email") String email){
        return !userService.isEmailExists(email);
    }

    @GetMapping("/phone/{number}")
    public boolean isPhoneFree(@PathVariable("number") String number){
        return !userService.isPhoneExists(number);
    }

    @GetMapping("/customers/search/{criteria}")
    public List<String> getCustomersSearchSuggestions(@PathVariable("criteria") String criteria){
        List<String> searchResult = userService.findCustomerData(criteria).stream().limit(10).toList();
        log.info("Endpoint result: {}", searchResult);
        return searchResult;
    }

    @PostMapping(value = "/customers/search/full-name")
    public List<String> getCustomersFullNameSuggestions(MultipartHttpServletRequest request){
        List<String> searchResult = userService
                .getCustomersNamesByItsSubstring(request.getParameter("name")).stream()
                .map(c -> c.getLName().strip() + ' ' + c.getFName().strip() + ' ' + c.getFatherName().strip())
                .limit(10).toList();
        log.info("Endpoint result: {}", searchResult);
        return searchResult;
    }

    @GetMapping("/orders/search/{criteria}")
    public List<String> getOrdersSearchSuggestions(@PathVariable("criteria") String criteria){
        return orderService.getSuggestionsByCriteria(criteria).stream().limit(10).toList();
    }

    @GetMapping("/goods/search/{criteria}")
    public List<String> getGoodsSearchSuggestions(@PathVariable("criteria") String criteria){
        return new HashSet<>(productService.getStringsByParameter(criteria)).stream()
                .limit(10).toList();
    }

    @GetMapping("/orders/unchecked-count")
    public int getUncheckedOrdersCount(){
        return orderService.getUncheckedCount();
    }

    @GetMapping("/work-price")
    public List<String> getWorkPrice() {
        try {
            return List.of(TXTReader.getParameter("workPrice"));
        } catch (IOException e){
            log.error("Error: {}: {}! \n{}", e.getClass(), e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    @GetMapping("/work-price/new/{price}")
    public List<String> setWorkPrice(@PathVariable("price") String price) {
        try {
            return List.of(TXTReader.setParameter("workPrice", price));
        } catch (IOException e){
            log.error("Error: {}: {}! \n{}", e.getClass(), e.getMessage(), e.getStackTrace());
        }
        return List.of(price);
    }

    @GetMapping("user-id")
    public List<String> getAuthUserId(Authentication auth){
        log.info("getAuthUserId() controller start! user login: {}", auth.getName());
        return List.of(userService.getByLogin(auth.getName()).getId().strip());
    }
}