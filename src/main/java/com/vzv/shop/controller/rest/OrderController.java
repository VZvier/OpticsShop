package com.vzv.shop.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.constructor.GlassesConstructor;
import com.vzv.shop.entity.order.ConstructorOrderLine;
import com.vzv.shop.entity.order.Order;
import com.vzv.shop.entity.order.OrderLine;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.enumerated.Status;
import com.vzv.shop.request.ConstructorRequest;
import com.vzv.shop.request.OrderLineRequest;
import com.vzv.shop.service.GlassesConstructorService;
import com.vzv.shop.service.OrderService;
import com.vzv.shop.service.ProductService;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/shop/orders")
public class OrderController {

    private static final String TIME = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy").format(LocalDateTime.now());

    private final OrderService orderService;
    private final GlassesConstructorService constructorService;
    private final ProductService productService;
    private final UserService userService;

    public OrderController(OrderService orderService,
                           GlassesConstructorService constructorService, ProductService productService,
                           UserService userService) {
        this.orderService = orderService;
        this.constructorService = constructorService;
        this.productService = productService;
        this.userService = userService;
    }


    @GetMapping("by-id/{id}")
    public Order getById(@PathVariable("id") String orderId) {
        return orderService.getById(orderId);
    }

    @GetMapping("of-user/{userId}")
    public List<Order> getUsersOrders(@PathVariable("userId") String userId) {
        return orderService.getAllUserOrders(userId);
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createOrder(MultipartHttpServletRequest request, Authentication auth) throws JsonProcessingException {
        String orderId = RandomStringUtils.randomAlphanumeric(12);
        ObjectMapper mapper = new ObjectMapper();
        List<OrderLineRequest> olsDto = mapper.readValue(request
                .getParameter("orderLines[]"), new TypeReference<List<OrderLineRequest>>() {
        });
        List<ConstructorRequest> constrRequests = mapper.readValue(request
                .getParameter("constructors[]"), new TypeReference<List<ConstructorRequest>>() {
        });

        List<ProductDTO> conProducts = productService.getAllByIds(constrRequests.stream()
                .flatMap(c -> Stream.of(c.getFrame(), c.getOdLens(), c.getOsLens())).collect(Collectors.toList()));

        List<GlassesConstructor> constructors = AuxiliaryUtils.makeConstructorOrderLines(constrRequests, conProducts);
        if (!constructors.isEmpty()) {
            constructorService.saveAll(constructors);
        }
        Customer customer = userService.getCustomerById((request.getParameter("customerId") != null
                ? request.getParameter("customerId") : userService.getByLogin(auth.getName()).getId().strip()));

        String deliveryAddress = request.getParameter("address") != null
                ? request.getParameter("address") : customer.getAddress().toInlineOrEmpty();
        List<ProductDTO> products = productService.getAllByIds(olsDto.stream()
                .map(ol -> ol.getProductId().strip()).toList());
        List<OrderLine> orderLines = AuxiliaryUtils.makeOrderLines(olsDto, products);
        List<ConstructorOrderLine> constructorOrderLines = constructors.stream().map(constructor ->
                new ConstructorOrderLine(orderId, constructor, constructor.getQuantity(), constructor.getPrice())
        ).collect(Collectors.toList());

        Order order = new Order(orderId, customer, orderLines, constructorOrderLines,
                deliveryAddress, TIME, TIME, Status.PROCESSING);
        order = orderService.save(order);
        log.info("Order 2: {}", order.getId());
        return order.getId().strip();
    }

    @PutMapping(value = "/renew", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order updateOrder(MultipartHttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(request.getParameter("order"), new TypeReference<Order>() {
        });
        if (request.getParameter("constructors[]") != null) {
            List<ConstructorOrderLine> cOLs = mapper
                    .readValue(request.getParameter("constructors[]"), new TypeReference<List<ConstructorOrderLine>>() {
                    });
            order.setConstructors(cOLs);
        }
        if (request.getParameter("orderLines[]") != null) {
            List<OrderLineRequest> olsDTO = mapper
                    .readValue(request.getParameter("orderLines[]"), new TypeReference<List<OrderLineRequest>>() {
                    });
            List<ProductDTO> products = productService.getAllByIds(olsDTO.stream().map(ol -> ol.getProductId().strip()).toList());
            List<OrderLine> newOrderLines = olsDTO.stream().map(OrderLineRequest::toOrderLine)
                    .peek(ol -> {
                        ol.setId(RandomStringUtils.randomAlphanumeric(12));
                        ProductDTO pDto = products.stream().filter(p -> p.getId().strip().equals(ol.getProduct().getId().strip())).toList().get(0);
                        log.info("In orderLine looking product: {}", pDto);
                        ol.setProduct(pDto);
                        ol.setPrice(ol.getProduct().getPrice());
                        log.info("\nOL: {}", ol);
                    }).collect(Collectors.toList());
            newOrderLines.addAll(order.getOrderLines());
            order.setOrderLines(newOrderLines);
        }
        order.setUpdated(TIME);
        order.setStatus(Status.getByLabel(order.getStatus()).getLabel());
        Order dbOrder = orderService.getById(order.getId());
        AuxiliaryUtils.copy(order, dbOrder, "id");
        orderService.update(dbOrder);
        log.info("UpdateController: {}", dbOrder);
        return dbOrder;
    }

    @DeleteMapping("/rm/{id}")
    private void deleteOrder(@PathVariable("id") String id) {
        orderService.deleteById(id);
    }

    @GetMapping("/search/{idOf}/{parameter}")
    public List<Order> findOrders(@PathVariable("parameter") String id,
                                  @PathVariable("idOf") String idOf) {
        switch (idOf) {
            case "customer" -> {
                return orderService.getCustomersOrders(id);
            }
            case "order" -> {
                Order order = orderService.getById(id);
                return order != null ? List.of(order) : new ArrayList<>();
            }
        }
        return null;
    }
}
