package com.vzv.shop.picture;

import com.vzv.shop.DataForTests;
import com.vzv.shop.dto.PictureDto;
import com.vzv.shop.entity.Picture;
import com.vzv.shop.entity.product.Product;
import com.vzv.shop.repository.PictureDtoRepository;
import com.vzv.shop.repository.PictureRepository;
import com.vzv.shop.service.implementation.PictureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {

    @Mock
    private PictureRepository picRepo;
    @Mock
    private PictureDtoRepository picDtoRepo;

    @InjectMocks
    private PictureServiceImpl picService;

    private final DataForTests data = new DataForTests();

    @Test
    void testGetAllPictures() {
        List<Picture> expectedResult = data.getPicturesList();

        when(picRepo.findAll()).thenReturn(expectedResult);
        List<Picture> actualResult = picService.getAllPictures();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetPicturesByIds() {
        List<Picture> expectedResult = data.getPicturesList();

        when(picRepo.findAllById(expectedResult
                .stream().map(Picture::getId).toList())).thenReturn(expectedResult);
        List<Picture> actualResult = picService.getPicturesByIds(expectedResult
                .stream().map(Picture::getId).toList());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetPictureById() {
        Picture expectedResult = data.getPicturesList().get(0);

        when(picRepo.findById(anyString())).thenReturn(Optional.ofNullable(expectedResult));
        Picture actualResult = picService.getPictureById(anyString());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByProductId() {
        Product product = data.getProductList().get(0);
        List<PictureDto> expectedResult = data.getPicturesDtoList().stream()
                .filter(pic ->
                        pic.getProducts().stream().map(Product::getId).toList()
                        .contains(product.getId())
                )
                .toList();

        when(picDtoRepo.findPicturesByProductId(anyString())).thenReturn(expectedResult);
        List<PictureDto> actualResult = picService.getByProductId(anyString());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetPictureByName() {
        Picture expectedResult = data.getPicturesList().get(0);

        when(picRepo.findPictureByName(expectedResult.getName())).thenReturn(Optional.of(expectedResult));
        Picture actualResult = picService.getPictureByName(expectedResult.getName());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSavePicture() {
        Picture expectedResult = data.getPicturesList().get(0);

        when(picRepo.save(any())).thenReturn(expectedResult);
        Picture actualResult = picService.savePicture(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveAllPictures() {
        List<Picture> expectedResult = data.getPicturesList();

        when(picRepo.saveAll(expectedResult)).thenReturn(expectedResult);
        List<Picture> actualResult = picService.saveAllPictures(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdatePicture() {
        Picture expectedResult = data.getPicturesList().get(0);

        when(picRepo.save(any())).thenReturn(expectedResult);
        Picture actualResult = picService.updatePicture(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeletePicture() {
        boolean expectedResult = true;

        doNothing().when(picRepo).deletePictureById(anyString());
        when(picRepo.existsById(anyString())).thenReturn(false);
        boolean actualResult = picService.deletePicture(anyString());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeletePictureByName() {
        Picture img = data.getPicturesList().get(0);
        boolean expectedResult = true;

        when(picRepo.deleteByName(img.getName())).thenReturn(true);
        boolean actualResult = picService.deletePictureByName(img.getName());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteAllPictures() {
        boolean expectedResult = true;

        doNothing().when(picRepo).deleteAll();
        when(picRepo.count()).thenReturn(0L);
        boolean actualResult = picService.deleteAllPictures();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsExistsById() {
        Picture img = data.getPicturesList().get(0);
        boolean expectedResult = true;

        when(picRepo.existsById(img.getId())).thenReturn(true);
        boolean actualResult = picService.isExistsById(img.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsExistsByName() {
        Picture img = data.getPicturesList().get(0);
        boolean expectedResult = true;

        when(picRepo.existsByName(img.getId())).thenReturn(true);
        boolean actualResult = picService.isExistsByName(img.getId());

        assertEquals(expectedResult, actualResult);
    }
}