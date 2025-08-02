package com.vzv.shop.service;

import com.vzv.shop.dto.PictureDto;
import com.vzv.shop.entity.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> getAllPictures();
    List<Picture> getPicturesByIds(List<String> ids);
    Picture getPictureById(String id);
    List<PictureDto> getByProductId(String productId);
    Picture getPictureByName(String name);
    boolean isExistsByName(String name);
    boolean isExistsById(String id);
    Picture savePicture(Picture picture);
    List<Picture> saveAllPictures(List<Picture> pictures);
    Picture updatePicture(Picture picture);
    boolean deletePicture(String pictureId);
    boolean deleteAllPictures();
    boolean deletePictureByName(String name);
}
