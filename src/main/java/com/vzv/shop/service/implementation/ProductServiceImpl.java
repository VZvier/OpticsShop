package com.vzv.shop.service.implementation;

import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.data.CSVFileReader;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.product.*;
import com.vzv.shop.enumerated.FrameSize;
import com.vzv.shop.enumerated.FrameType;
import com.vzv.shop.enumerated.Gender;
import com.vzv.shop.enumerated.LensType;
import com.vzv.shop.repository.PictureRepository;
import com.vzv.shop.repository.ProductDtoRepository;
import com.vzv.shop.repository.ProductRepository;
import com.vzv.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final PictureRepository pictureRepository;

    private final ProductDtoRepository dtoRepository;

    public ProductServiceImpl(ProductRepository repository,
                              PictureRepository pictureRepository,
                              ProductDtoRepository dtoRepository) {
        this.repository = repository;
        this.pictureRepository = pictureRepository;
        this.dtoRepository = dtoRepository;
    }

    @Override
    public List<ProductDTO> getAll() {
        return dtoRepository.findAll();
    }


    @Override
    public Product getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDTO> getAllByIds(List<String> idList) {
        return dtoRepository.findAllById(idList);
    }

    @Override
    public ProductDTO getDtoById(String id) {
        return dtoRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDTO> getAllDtoByIds(List<String> ids) {
        return dtoRepository.findAllById(ids);
    }

    @Override
    public boolean isExistsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public List<Product> saveAllProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product toUpdate = repository.findById(product.getId()).orElseThrow();
        toUpdate = AuxiliaryUtils.copy(product, toUpdate, "id");
        return repository.save(toUpdate);
    }

    @Override
    public boolean deleteProduct(String id) {
        repository.deleteById(id);
        log.info("Deleted deleted products!");
        return repository.findById(id).orElse(null) == null;
    }

    @Override
    public boolean deleteAll() {
        repository.deleteAll();
        pictureRepository.deleteAll();
        return repository.count() == 0 && pictureRepository.count() == 0;
    }

    @Override
    public boolean isEmpty() {
        return repository.count() == 0;
    }

    @Override
    public List<String> getAllBrands() {
        return repository.findAllBrands();
    }

    @Override
    public List<String> getBrandModels(String brand) {
        return dtoRepository.getAllModelsByBrand(brand);
    }

    @Override
    public List<String> getStringsByParameter(String param) {
        switch (param) {
            case "country" -> {
                return CSVFileReader.COUNTRIES.keySet().stream().toList();
            }
            case "countries" -> {
                return dtoRepository.getAllCountries();
            }
            case "brand" -> {
                return dtoRepository.getAllBrands();
            }
            case "model" -> {
                return dtoRepository.getAllModels();
            }
            case "nomination" -> {
                return repository.findAllNominations();
            }
            case "description" -> {
                return dtoRepository.getAllDescriptions();
            }
            case "lensType" -> {
                return LensType.getLabels();
            }
            case "frameType" -> {
                return FrameType.getLabels();
            }
            case "frameSize" -> {
                return FrameSize.getLabels();
            }
            case "gender" -> {
                return Gender.getLabels();
            }
            case "available" -> {
                return List.of("true", "false");
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<String> getIntsByParameter(String param, @Nullable String spare) {
        switch (param){
            case "price" -> {
                return dtoRepository.getAllPrices();
            }
            case "volume" -> {
                if (spare == null) {
                    return dtoRepository.getAllLiquidVolumes();
                } else {
                    return dtoRepository.getAllLiquidVolumes(spare);
                }
            }
            case "sp" -> {
                if (spare == null) {
                    return dtoRepository.getAllLensDiopters();
                } else {
                    return  dtoRepository.getAllRGDiopters();
                }
            }
            case "cyl" -> {
                return dtoRepository.getAllLensCylinders();
            }
            case "distance" -> {
                return dtoRepository.getAllDistances();
            }
            case "coeff" -> {
                return dtoRepository.getAllCoefficients();
            }
            default ->{
                return new ArrayList<>();
            } 
        }
    }

    @Override
    public List<String> getAllNominations() {
        return repository.findAllNominations();
    }

    @Override
    public List<String> getLiquidVolumes(String brand) {
        return dtoRepository.getAllLiquidVolumes(brand);
    }

    @Override
    public Product getProductForm(String type, MultipartHttpServletRequest params){
        return switch (type.strip()) {
            case "frame" -> params != null ? new Frame(params) : new Frame();
            case "lens" -> params != null ? new Lens(params) : new Lens();
            case "ready-glasses" -> params != null ? new ReadyGlasses(params) : new ReadyGlasses();
            case "liquid" -> params != null ? new Liquid(params) : new Liquid();
            case "medical-equipment" -> params != null ? new MedicalEquipment(params) : new MedicalEquipment();
            default -> params != null ? new Other(params) : new Other();
        };
    }

    @Override
    public String makeId(String entityType){
        String generatedId = RandomStringUtils.randomAlphanumeric(10);
        if (entityType.equals("picture")){
            while (pictureRepository.existsById(generatedId)){
                generatedId = RandomStringUtils.randomAlphanumeric(10);
            }
        } else {
            while (isExistsById(generatedId)){
                generatedId = RandomStringUtils.randomAlphanumeric(10);
            }
        }
        return generatedId;
    }

    @Override
    public Picture isImgExistOrNew(MultipartFile file) throws IOException {
        if (!pictureRepository.existsByName(file.getOriginalFilename())) {
            log.info("Picture doesn't exists by name: {}!", file.getOriginalFilename());
            return new Picture(
                    makeId("picture"),
                    Objects.requireNonNull(file.getOriginalFilename()),
                    Objects.requireNonNull(file.getContentType()),
                    file.getBytes()
                );
        } else {
            Picture img = pictureRepository.findPictureByName(file.getOriginalFilename()).orElse(null);
            log.info("New picture doesn't created! Get existed {}!", img);
            return img;
        }
    }

    @Override
    public void changePricesByCriteria(String by, String action, String criteria, Integer number) {
        List<ProductDTO> goodsDto = dtoRepository.findAllByCriteria(criteria);
        goodsDto = goodsDto.isEmpty() ? dtoRepository.findAll().stream()
                    .filter(prod -> StringUtils.containsIgnoreCase(prod.getNomination(), criteria)
                            || StringUtils.containsIgnoreCase(prod.getBrand(), criteria)
                            || StringUtils.containsIgnoreCase(prod.getModel(), criteria))
                    .collect(Collectors.toList()) : goodsDto;
        List<Product> goods = repository.findAllById( goodsDto.stream().map(ProductDTO::getId).map(String::strip).toList() );
        log.info("Goods first: {}", (goods.isEmpty() ? "notFound" : goods.get(0).toString()));
        if (!goods.isEmpty()) {
            log.info("Goods !empty !!");
            goods = goods.stream()
                    .peek(prod -> {
                        if (!by.equals("number")){
                            prod.changePriceByPercent(action, number);
                        } else {
                            prod.changePriceByValue(action, number);
                        }
                    }).toList();
            log.info("Goods first before save: {}", (goods.isEmpty() ? "notFound" : goods.get(0).toString()));
            repository.saveAllAndFlush(goods);
        }
        log.warn("Goods is Empty! Method complete!");
    }
}