package com.vzv.shop.controller.rest;

import com.vzv.shop.entity.user.Address;
import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.security.CustomAuthenticationProvider;
import com.vzv.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shop/api/users/")
public class AuthController {

    private final CustomAuthenticationProvider provider;
    private final UserService userService;

    public AuthController(CustomAuthenticationProvider provider, UserService userService) {
        this.provider = provider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ModelAndView signIn(@RequestParam("password") String password,
                         @RequestParam("login") String login,
                         @Nullable @RequestParam("positionToReturn") String returnUrl,
                         HttpServletRequest request) {
        log.info("Start signIn() method! For {}.", login);
        Map<String, Object> model = new HashMap<>();
        if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
            model.put("warn", "Не введено имя пользователя или пароль!");
            return new ModelAndView("pages/login", model);
        } else {
            SecurityContext context = SecurityContextHolder.getContext();
            try {
                Authentication auth = provider.authenticate(
                        new UsernamePasswordAuthenticationToken(login, password));
                context.setAuthentication(auth);
            } catch (BadCredentialsException e){
                model.put("warn", e.getMessage());
                return new ModelAndView("pages/login", model);
            }
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            log.info("Is {} authenticated - {}!", login, context.getAuthentication().isAuthenticated());
            if (context.getAuthentication().isAuthenticated()){
                User user = userService.getByLogin(login);
                user.setLastVisit(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()));
                userService.updateUser(userService.getByLogin(login));
            }
            if (!StringUtils.isBlank(returnUrl)){
                log.info("Return url: {}", returnUrl);
                return new ModelAndView("redirect:" + returnUrl, model);
            } else {
                return new ModelAndView("redirect:/", model);
            }
        }
    }

    @PostMapping(value = "registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> register(MultipartHttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        String id = userService.generateId();
        User user = new User(id, request);
        Contact  contact = new Contact(id, request);
        Customer customer = new Customer(id, request);
        Address address = new Address(id, request);

        userService.save(user, customer, contact, address);
        Customer savedCustomer = userService.getCustomerById(id);
        model.put("welcomeMessage", "Регисмтрация пользователя "
                + savedCustomer.getUser().getLogin() + " успешна!\n Пожалуйста, " + savedCustomer.getFName()
                + " " + savedCustomer.getFatherName() + " выполните вход!");
        model.put("customer", savedCustomer);
        return model;
    }
}