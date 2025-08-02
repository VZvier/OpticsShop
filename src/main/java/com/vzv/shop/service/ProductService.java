package com.vzv.shop.service;

import com.vzv.shop.dto.ProductDTO;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.product.Product;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    Product getById(String id);

    List<ProductDTO> getAllByIds(List<String> idList);

    List<ProductDTO> getAllDtoByIds(List<String> ids);

    ProductDTO getDtoById(String id);

    boolean isExistsById(String id);

    List<Product> saveAllProducts(List<Product> products);

    Product save(Product product);

    Product updateProduct(Product product);

    boolean deleteProduct(String id);

    boolean deleteAll();

    boolean isEmpty();

    List<String> getAllBrands();

    List<String> getBrandModels(String brand);
    List<String> getStringsByParameter(String param);
    List<String> getIntsByParameter(String param, @Nullable String spare);

    List<String> getAllNominations();

    List<String> getLiquidVolumes(String brand);

    Product getProductForm(String type, MultipartHttpServletRequest params);

    String makeId(String entityType);

    Picture isImgExistOrNew(MultipartFile file) throws IOException;

    void changePricesByCriteria(String by, String action, String criteria, Integer percent);
}
