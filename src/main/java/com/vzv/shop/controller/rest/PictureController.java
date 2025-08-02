package com.vzv.shop.controller.rest;

import com.vzv.shop.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @DeleteMapping("/pictures/rm/{id}")
    public void deleteImage(@PathVariable("id") String id){
        pictureService.deletePicture(id);
    }

    @DeleteMapping("/pictures/rm-by-name/{name}")
    public void deleteImageByName(@PathVariable("name") String name){
        pictureService.deletePicture(name);
    }
}
