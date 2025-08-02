package com.vzv.shop.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.product.Product;
import com.vzv.shop.service.PictureService;
import com.vzv.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop/products")
public class ProductController {

    private final ProductService productService;
    private final PictureService picService;

    public ProductController(ProductService productService, PictureService picService) {
        this.productService = productService;
        this.picService = picService;
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") String id) {
        log.info("Get product!");
        return productService.getById(id);
    }

    @GetMapping("all")
    public List<ProductDTO> getAllProductDto() {
        log.info("Get all goods DTO! url: '/shop/products/all' !");
        return productService.getAll();
    }

    @GetMapping("all-by-nomination/{nomination}")
    public List<ProductDTO> getByNomination(@PathVariable("nomination") String nomination) {
        return productService.getAll().stream().filter(product -> product.getNomination().equals(nomination)).toList();
    }

    @GetMapping("by-nomination-and-brand/{nomination}/{brand}")
    public List<ProductDTO> getByNominationAndBrand(@PathVariable("nomination") String nomination,
                                                    @PathVariable("brand") String brand) {
        return productService.getAll().stream()
                .filter(product ->
                        product.getNomination().strip().equals(nomination)
                        && product.getBrand().strip().equals(brand)
                )
                .toList();
    }

    @GetMapping("/forms/{type}")
    public Product getEmptyForm(@PathVariable("type") String type) {
        Product emptyForm = productService.getProductForm(type, null);
        emptyForm.setId(productService.makeId("product"));
        return emptyForm;
    }

    @PostMapping(value = "/new/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product addNewProduct(MultipartHttpServletRequest request) throws IOException {
        Product product = productService.getProductForm(request.getParameter("nomination"), request);
        if (product.getId() == null) {
            product.setId(productService.makeId("product"));
        }
        List<Picture> pictures = new ArrayList<>();
        List<MultipartFile> files = request.getFiles("pictures[]");
        for (MultipartFile file : files) {
            pictures.add(productService.isImgExistOrNew(file));
        }
        product.setPictures(pictures);
        return productService.save(product);
    }

    @PutMapping(value = "/renew", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(MultipartHttpServletRequest request) throws IOException {
        Product dataToUpdate = productService.getProductForm(request.getParameter("nomination"), request);
        if (request.getParameter("existedPictures[]") != null) {
            ObjectMapper mapper = new ObjectMapper();
            List<Picture> existedPictures = picService.getPicturesByIds(
                    mapper.readValue(request.getParameter("existedPictures[]"), new TypeReference<List<Picture>>() {
                            })
                            .stream().map(Picture::getId).toList()
            );
            dataToUpdate.setPictures(existedPictures);
        }
        List<Picture> newPictures = new ArrayList<>();
        for (MultipartFile file : request.getFiles("pictures[]")) {
            newPictures.add(productService.isImgExistOrNew(file));
        }
        dataToUpdate.setPictures(newPictures);
        productService.updateProduct(dataToUpdate);
        return productService.getById(dataToUpdate.getId());
    }

    @PutMapping("price/renew/{by}")
    public void updateGoodsPrices(@PathVariable("by") String by, MultipartHttpServletRequest request){
        String action = request.getParameter("action");
        String criteria = request.getParameter("criteria");
        Integer percent = Integer.parseInt(request.getParameter("percent"));
        productService.changePricesByCriteria(by, action, criteria, percent);
    }

    @DeleteMapping("rm-all")
    public void clearProductsTable(){
        if (productService.deleteAll()){
            log.info("Table products is clear!");
        }
    }

}