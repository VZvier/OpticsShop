package com.vzv.shop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationConfig {

    private final String KEY = "Aspect-Optics_Application_secretSecurity_Key_for_rememberMe_Authentication";

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] auth = {"USER", "STAFF", "SYSADMIN"};
        String[] staff = {"STAFF", "SYSADMIN"};

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) ->
                        request
                                .requestMatchers("/", "/shop/api/users/registration", "/shop/api/users/login",
                                        "/shop/api/constructor", "/shop/data/**", "/shop/products/page",
                                        "/shop/products/details/*", "/shop/products/all",
                                        "/shop/products/all-by-nomination/*",
                                        "/shop/products/by-nomination-and-brand/**", "/shop/orders/basket/page",
                                        "/shop/orders/basket", "/shop/orders/basket-products",
                                        "/style.css", "/no-image.png", "/favicon.png", "/user-img2.jpg")
                                .permitAll()

                                .requestMatchers(HttpMethod.POST, "/shop/api/users/registration",
                                        "/shop/api/users/login")
                                .permitAll()

                                .requestMatchers(HttpMethod.GET, "/shop/api/users/auth-customer/info",
                                        "/shop/api/users/auth-user/info", "/shop/api/users/by-id/*",
                                        "/shop/api/users/customer/*", "/shop/api/users/search/*",
                                        "/shop/api/users/renew/customer/*", "/shop/api/users/full/by-id/*",
                                        "/shop/api/users/page/**", "/shop/api/users/address/page",
                                        "/shop/api/users/address", "/shop/orders/all", "/shop/orders/page/",
                                        "/shop/orders/page/*", "/shop/orders/customer/*",
                                        "/shop/orders/by-user-id/*", "/shop/orders/by-id/*",
                                        "/shop/orders/by_date_of_creation/**", "/shop/orders/by_date_of_updating/**",
                                        "/shop/orders/my-orders", "/shop/orders/renew/*",
                                        "/shop/orders/saved-order/*", "/shop/api/settlements/page")
                                .hasAnyRole(auth)

                                .requestMatchers(HttpMethod.POST, "/shop/api/users/customer/add-new",
                                        "/shop/api/users/renew/customer", "/shop/orders/new",
                                        "/shop/api/regions/new", "/shop/api/cities/new",
                                        "/shop/api/streets/new", "/shop/data/**")
                                .hasAnyRole(auth)

                                .requestMatchers(HttpMethod.PUT, "/shop/api/users**", "/shop/api/users/**",
                                        "/shop/orders/renew", "/shop/api/users/address/renew/*")
                                .hasAnyRole(auth)

                                .requestMatchers(HttpMethod.DELETE, "/shop/orders/rm/*")
                                .hasAnyRole(auth)

                                .requestMatchers(HttpMethod.GET, "/shop/api/administration", "/shop/api/users/new",
                                        "/shop/api/users/renew/customer", "/shop/api/users/customers/all",
                                        "/shop/products/new/", "/shop/products/renew", "/shop/products/forms/*",
                                        "/shop/products/renew/*", "/shop/orders/new", "/shop/orders/per/*",
                                        "/shop/orders/unchecked", "/shop/data/orders/unchecked-count",
                                        "/shop/products/*", "/shop/orders/by-nomination-and-brand/**")
                                .hasAnyRole(staff)

                                .requestMatchers(HttpMethod.POST, "/shop/api/users/new", "/shop/products/new/")
                                .hasAnyRole(staff)

                                .requestMatchers(HttpMethod.PUT, "/shop/products/price/**", "/shop/products/renew")
                                .hasAnyRole(staff)

                                .requestMatchers(HttpMethod.DELETE, "/shop/api/users/rm/*", "/shop/products/rm/**",
                                        "/pictures/**")
                                .hasAnyRole(staff)

                                .requestMatchers(HttpMethod.DELETE, "/shop/api/users/rm/customer/*",
                                        "/shop/products/rm-all")
                                .hasRole("SYSADMIN")
                )
                .rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(60 * 60 * 10).key(KEY))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }

}