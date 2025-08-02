package com.vzv.shop.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.entity.sattlemants.City;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.sattlemants.Street;
import com.vzv.shop.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop/api/")
public class SettlementAndStreetController {

    private final SettlementService service;

    public SettlementAndStreetController(SettlementService service) {
        this.service = service;
    }

    @GetMapping("settlements/page")
    public ModelAndView getSettlementsPage(){
        return new ModelAndView("pages/settlements");
    }

    @PostMapping(value = "regions/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveRegions(MultipartHttpServletRequest servletRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Got regions[]: {}", servletRequest.getParameter("regions[]"));
        List<Region> regions = mapper.readValue(
                servletRequest.getParameter("regions[]"),
                new TypeReference<List<Region>>() {});
        log.info("Saved streets: {}", service.saveRegions(regions));
        return "Regions were saved!";
    }

    @PostMapping(value = "cities/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCities(MultipartHttpServletRequest servletRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Got cities[]: {}", servletRequest.getParameter("cities[]"));
        List<City> cities = mapper.readValue(
                servletRequest.getParameter("cities[]"),
                new TypeReference<List<City>>() {});

        log.info("Saved cities: {}", service.saveCities(cities));
        return "Cities were saved!";
    }

    @PostMapping(value = "streets/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveStreets(MultipartHttpServletRequest servletRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Got streets[]: {}", servletRequest.getParameter("streets[]"));
        List<Street> streets = mapper.readValue(
                servletRequest.getParameter("streets[]"),
                new TypeReference<List<Street>>() {});

        log.info("Saved streets: {}", service.saveStreets(streets));
        return "Streets were saved!";
    }
}
