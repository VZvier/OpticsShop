package com.vzv.shop.service;

import com.vzv.shop.entity.sattlemants.City;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.sattlemants.Street;

import java.util.List;

public interface SettlementService {


    List<String> getRegions(String lang);
    List<Region> saveRegions(List<Region> regions);
    List<City> saveCities(List<City> cities);
    List<Street> saveStreets(List<Street> streets);
}
