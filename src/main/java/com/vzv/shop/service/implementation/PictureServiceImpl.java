package com.vzv.shop.service.implementation;

import com.vzv.shop.dto.PictureDto;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.repository.PictureDtoRepository;
import com.vzv.shop.repository.PictureRepository;
import com.vzv.shop.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final PictureDtoRepository dtoRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, PictureDtoRepository dtoRepository) {
        this.pictureRepository = pictureRepository;
        this.dtoRepository = dtoRepository;
    }

    @Override
    public List<Picture> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public List<Picture> getPicturesByIds(List<String> ids) {
        return pictureRepository.findAllById(ids);
    }

    @Override
    public Picture getPictureById(String id) {
        return pictureRepository.findById(id).orElse(null);
    }

    @Override
    public List<PictureDto> getByProductId(String productId) {
        return dtoRepository.findPicturesByProductId(productId);
    }

    @Override
    public Picture getPictureByName(String name) {
        return pictureRepository.findPictureByName(name.trim()).orElse(null);
    }

    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> saveAllPictures(List<Picture> pictures) {
        return pictureRepository.saveAll(pictures);
    }

    @Override
    public Picture updatePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public boolean deletePicture(String pictureId) {
        pictureRepository.deletePictureById(pictureId);
        return !pictureRepository.existsById(pictureId);
    }

    @Override
    public boolean deletePictureByName(String name) {
        return pictureRepository.deleteByName(name);
    }

    @Override
    public boolean deleteAllPictures() {
        pictureRepository.deleteAll();
        return pictureRepository.count() == 0;
    }

    @Override
    public boolean isExistsById(String id) {
        return pictureRepository.existsById(id);
    }

    @Override
    public boolean isExistsByName(String name) {
        return pictureRepository.existsByName(name);
    }
}
