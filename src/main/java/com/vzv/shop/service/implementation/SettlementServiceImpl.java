package com.vzv.shop.service.implementation;

import com.vzv.shop.entity.sattlemants.City;
import com.vzv.shop.entity.sattlemants.Region;
import com.vzv.shop.entity.sattlemants.Street;
import com.vzv.shop.repository.settlements.CityRepository;
import com.vzv.shop.repository.settlements.RegionRepository;
import com.vzv.shop.repository.settlements.StreetRepository;
import com.vzv.shop.service.SettlementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementServiceImpl implements SettlementService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;

    public SettlementServiceImpl(RegionRepository regionRepository, CityRepository cityRepository, StreetRepository streetRepository) {
        this.regionRepository = regionRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
    }

    @Override
    public List<String> getRegions(String lang) {
        switch (lang){
            case("Ru") -> { return regionRepository.findAll().stream().map(Region::getNameRu).toList(); }
            case("Ua") -> { return regionRepository.findAll().stream().map(Region::getNameUA).toList(); }
            default-> {return regionRepository.findAll().stream().map(Region::getNameEn).toList(); }
        }
    }

    @Override
    public List<Region> saveRegions(List<Region> regions) {
        return regionRepository.saveAll(regions);
    }

    @Override
    public List<City> saveCities(List<City> cities) {
        return cityRepository.saveAll(cities);
    }

    @Override
    public List<Street> saveStreets(List<Street> streets) {
        return streetRepository.saveAll(streets);
    }
}
