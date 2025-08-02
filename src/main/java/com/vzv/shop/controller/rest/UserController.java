package com.vzv.shop.controller.rest;

import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.entity.user.*;
import com.vzv.shop.service.UserService;
import com.vzv.shop.service.implementation.FullUserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/shop/api/users")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder bCrypt = new BCryptPasswordEncoder();
    private final FullUserServiceImpl fullUserService;

    public UserController(UserService userService, PasswordEncoder encoder, FullUserServiceImpl fullUserService) {
        this.userService = userService;
        this.fullUserService = fullUserService;
    }

    @GetMapping(value = "/address", produces = "application/json")
    public String getAuthUsersAddress(Authentication auth) {
        if (auth != null) {
            String id = userService.getByLogin(auth.getName()).getId().strip();
            Customer customer = userService.getCustomerById(id);
            if (customer != null) {
                String address = customer.getAddress().toInlineOrEmpty();
                log.info("Address: {}", address);
                return Objects.requireNonNullElse(address, "");
            } else {
                log.info("The user \"{}\" is not linked to any person!", auth.getName());
                return null;
            }
        } else {
            return null;
        }
    }

    @GetMapping("/auth-customer/info")
    public Customer getAuthUserFullInfo(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return userService.getCustomerById(userService.getByLogin(auth.getName()).getId().strip());
        } else {
            return null;
        }
    }

    @GetMapping("/auth-user/info")
    public User getAuthUserUserInfo(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return userService.getByLogin(auth.getName());
        } else {
            return null;
        }
    }

    @GetMapping("/by-id/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getById(id);
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable("id") String id) {
        return userService.getCustomerById(id);
    }

    @GetMapping("/search/{criteria}")
    public List<Customer> findCustomers(@PathVariable("criteria") String criteria) {
        log.info("Start UserMvcController.search()!");
        return userService.findCustomerByCriteria(criteria);
    }

    @GetMapping("/full/by-id/{id}")
    public FullUser getFullUserById(@PathVariable("id") String id) {
        return fullUserService.getById(id);
    }


    @PutMapping(value = "renew/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateProfile(MultipartHttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Customer updatable = new Customer(null, request);
        Customer customerFromDB = userService.getCustomerById(request.getParameter("id"));
        AuxiliaryUtils.copy(updatable, customerFromDB, "id");
        customerFromDB = userService.saveCustomer(customerFromDB);
        map.put("welcomeMessage", "Ваш профиль обновлён!");
        map.put("userInfo", customerFromDB);
        log.info("Controller  Update method completed!\n {}\n will be send to user!", customerFromDB);
        return map;
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createUser(MultipartHttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        String id = userService.generateId();
        User user = new User(id, request);
        String autoGenPass = AuxiliaryUtils.makeRandomString(12);
        user.setPassword(autoGenPass);
        Customer customer = new Customer(id, request);
        customer.setUser(user);
        Contact contact = new Contact(id, request);
        contact.setPhone(userService.reformatPhone(contact.getPhone()));
        contact.setEmail(userService.reformatEmail(contact.getEmail()));
        customer.setContact(new Contact(id, request));
        customer.setAddress(new Address(id, request));
        log.info("Controller got customer! \n{}", customer.toString());
        userService.saveCustomer(customer);
        Customer savedCustomer = userService.getCustomerById(id);
        if (savedCustomer != null) {
            model.put("customer", savedCustomer);
            model.put("pass", "Сгенерированный пароль: '" + autoGenPass + "'");
            model.put("message", "Customer " + savedCustomer + " saved successfully!");
        }
        return model;
    }

    @PostMapping("customer/add-new")
    public Customer addUserToCustomer(MultipartHttpServletRequest request){
        User user = new User(request.getParameter("id"), request);
        log.info("Created user: {}", user);
        userService.save(user);
        return userService.getCustomerById(user.getId());
    }

    @PostMapping(value = "renew/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer updateCustomer(MultipartHttpServletRequest request) {
        Customer newCustomer = new Customer("", request);
        newCustomer.setUser(new User("", request));
        newCustomer.setAddress(new Address("", request));
        newCustomer.setContact(new Contact("", request));

        Customer dbCustomer = userService.getCustomerById(request.getParameter("id"));
        dbCustomer = AuxiliaryUtils.copy(newCustomer, dbCustomer, "id");
        userService.saveCustomer(dbCustomer);
        dbCustomer = userService.getCustomerById(dbCustomer.getId());
        log.info("Updated customer: \n{}", dbCustomer);
        return dbCustomer;
    }

    @PutMapping("renew/customer/pass")
    public Customer changePassword(HttpServletRequest request) {
        Customer dbCustomer = userService.getCustomerById(request.getParameter("id"));
        if (dbCustomer != null) {
            User customerUser = dbCustomer.getUser();
            customerUser.setPassword(userService.encodePass(customerUser.getPassword().strip()));
            dbCustomer.setUser(customerUser);
            userService.saveCustomer(dbCustomer);
            return userService.getCustomerById(dbCustomer.getId().strip());
        } else {
            return null;
        }
    }

    @PutMapping("customer/renew-name")
    public String updateFullName(MultipartHttpServletRequest request){
        log.info("UpdateFullName method start!");
        String id = request.getParameter("id");
        Customer customer = userService.getCustomerById(id);
        System.out.println("Controller CustomerById: " + customer);
        customer.setLName(request.getParameter("newLastName"));
        customer.setFName(request.getParameter("newFirstName"));
        customer.setFatherName(request.getParameter("newFatherName"));
        log.info("Got from request {}, {}, {}, {}", id,
                request.getParameter("newLastName"),
                request.getParameter("newFirstName"),
                request.getParameter("newFatherName"));
        try{
            return userService.saveCustomer(customer).toStringFullName();
        } catch (Exception e) {
            log.error("Error! {}\n{}", e.getMessage(), e.getStackTrace());
        }
        return userService.getCustomerById(id).toStringFullName();
    }

    @PutMapping("customer/{id}/renew-prop/{propName}/{val}")
    public Customer updateProperty(@PathVariable("id") String id,
                                 @PathVariable("propName") String prop,
                                 @PathVariable("val") String val) {
        if (prop.equals("phone") || prop.equals("email")){
            val = prop.equals("phone") ? userService.reformatPhone(val) : userService.reformatEmail(val);
        }
        Customer customer = userService.getCustomerById(id);
        log.info("Controller got i: {}, prop name: {}, prop val: {}", id, prop, val);
        if(customer.hasProperty(prop)){
            return userService.saveCustomer(customer.invokeSetter(prop, val));
        } else if (prop.equals("password")) {
            User user = customer.getUser();
            user.setPassword(userService.encodePass(val));
            userService.updateUser(user);
        } else if (customer.getUser().hasProperty(prop)) {
            userService.updateUser(customer.getUser().invokeSetter(prop, val));
        } else if (customer.getContact().hasProperty(prop)){
            return userService.saveContact(customer.getContact().invokeSetter(prop, val));
        } else {
            log.warn("No property: {} found in customer, user & contact entity!", prop);
        }
        return userService.getCustomerById(id);
    }

    @DeleteMapping("rm/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        return userService.delete(id);
    }

    @DeleteMapping("rm/customer/{id}")
    public boolean deleteCustomer(@PathVariable("id") String id) {
        return userService.deleteCustomer(id);
    }

}