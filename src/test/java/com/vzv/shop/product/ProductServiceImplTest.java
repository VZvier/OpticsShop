package com.vzv.shop.product;

import com.vzv.shop.DataForTests;
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
import com.vzv.shop.repository.ProductRepository;
import com.vzv.shop.repository.ProductDtoRepository;
import com.vzv.shop.service.implementation.ProductServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private PictureRepository pictureRepository;
    @Mock
    private ProductDtoRepository dtoRepository;

    @InjectMocks
    private ProductServiceImpl service;

    private MockedStatic<AuxiliaryUtils> mockAuxUtils;
    private MockedStatic<CSVFileReader> cvsReader;
    private final DataForTests data = new DataForTests();

    @BeforeEach
    void setUp() {
        try {
            mockAuxUtils = mockStatic(AuxiliaryUtils.class);
            cvsReader = Mockito.mockStatic(CSVFileReader.class);
        } catch (Exception e) {
            mockAuxUtils.close();
            cvsReader.close();
            mockAuxUtils = mockStatic(AuxiliaryUtils.class);
            cvsReader = Mockito.mockStatic(CSVFileReader.class);
        }
    }

    @AfterEach
    void tearDown() {
        mockAuxUtils.close();
        cvsReader.close();
    }

    @Test
    void testGetAll() {
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(dtoRepository.findAll()).thenReturn(expectedResult);
        List<ProductDTO> actualResult = service.getAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetById() {
        Product expectedResult = data.getProductList().get(0);

        when(repository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        Product actualResult = service.getById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllByIds() {
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(dtoRepository.findAllById(expectedResult.stream().map(ProductDTO::getId).toList()))
                .thenReturn(expectedResult);
        List<ProductDTO> actualResult = service.getAllByIds(expectedResult.stream().map(ProductDTO::getId).toList());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getDtoById() {
        ProductDTO expectedResult = data.getProductDTOList().get(0);

        when(dtoRepository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        ProductDTO actualResult = service.getDtoById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllDtoByIds() {
        List<ProductDTO> expectedResult = data.getProductDTOList();

        when(dtoRepository.findAllById(expectedResult.stream().map(ProductDTO::getId).toList())).thenReturn(expectedResult);
        List<ProductDTO> actualResult = service.getAllDtoByIds(expectedResult.stream().map(ProductDTO::getId).toList());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsExistsById() {
        ProductDTO product = data.getProductDTOList().get(0);
        boolean expectedResult = true;

        when(repository.existsById(product.getId())).thenReturn(expectedResult);
        boolean actualResult = service.isExistsById(product.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveAllProducts() {
        List<Product> expectedResult = data.getProductList();

        when(repository.saveAll(expectedResult)).thenReturn(expectedResult);
        List<Product> actualResult = service.saveAllProducts(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSave() {
        Product expectedResult = data.getProductList().get(0);

        when(repository.save(expectedResult)).thenReturn(expectedResult);
        Product actualResult = service.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdateProduct() {
        Product expectedResult = data.getProductList().get(0);

        when(repository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        mockAuxUtils.when(() -> AuxiliaryUtils.copy(any(), any(), anyString())).thenReturn(expectedResult);
        when(repository.save(expectedResult)).thenReturn(expectedResult);
        Product actualResult = service.updateProduct(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteProduct() {
        String id = data.getProductDTOList().get(0).getId();
        boolean expectedResult = true;

        doNothing().when(repository).deleteById(id);
        when(repository.findById(id)).thenReturn(Optional.empty());
        boolean actualResult = service.deleteProduct(id);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteAll() {
        boolean expectedResult = true;

        doNothing().when(repository).deleteAll();
        doNothing().when(pictureRepository).deleteAll();
        when(repository.count()).thenReturn(0L);
        when(pictureRepository.count()).thenReturn(0L);
        boolean actualResult = service.deleteAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsEmpty() {
        boolean expectedResult = true;

        when(repository.count()).thenReturn(0L);
        boolean actualResult = service.isEmpty();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllBrands() {
        List<String> expectedResult = data.getProductDTOList().stream().map(ProductDTO::getBrand).toList();

        when(repository.findAllBrands()).thenReturn(expectedResult);
        List<String> actualResult = service.getAllBrands();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetBrandModels() {
        List<ProductDTO> products = data.getProductDTOList();
        String brand = products.get(0).getBrand();
        List<String> expectedResult = products.stream().filter(p -> p.getBrand().equals(brand))
                .map(ProductDTO::getModel).toList();

        when(dtoRepository.getAllModelsByBrand(brand)).thenReturn(expectedResult);
        List<String> actualResult = service.getBrandModels(brand);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetStringsByParameter() {
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = List.of("Украина", "Англия", "Польша");


        when(dtoRepository.getAllCountries()).thenReturn(expectedResult);
        when(dtoRepository.getAllBrands()).thenReturn(products.stream()
                .map(ProductDTO::getBrand).toList());
        when(dtoRepository.getAllModels()).thenReturn(products.stream()
                .map(ProductDTO::getModel).filter(Objects::nonNull).toList());
        when(repository.findAllNominations()).thenReturn(products.stream()
                .map(ProductDTO::getNomination).toList());
        when(dtoRepository.getAllDescriptions()).thenReturn(products.stream()
                .map(ProductDTO::getDescription).filter(Objects::nonNull).toList());
        List<String> actualResult = service.getStringsByParameter("countries");

        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(ProductDTO::getBrand).toList();
        actualResult = service.getStringsByParameter("brand");

        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(ProductDTO::getModel).filter(Objects::nonNull).toList();
        actualResult = service.getStringsByParameter("model");

        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(ProductDTO::getNomination).filter(Objects::nonNull).toList();
        actualResult = service.getStringsByParameter("nomination");

        assertEquals(expectedResult, actualResult);

        expectedResult = products.stream().map(ProductDTO::getDescription).filter(Objects::nonNull).toList();
        actualResult = service.getStringsByParameter("description");

        assertEquals(expectedResult, actualResult);

        expectedResult = LensType.getLabels();
        actualResult = service.getStringsByParameter("lensType");

        assertEquals(expectedResult, actualResult);

        expectedResult = FrameType.getLabels();
        actualResult = service.getStringsByParameter("frameType");

        assertEquals(expectedResult, actualResult);

        expectedResult = FrameSize.getLabels();
        actualResult = service.getStringsByParameter("frameSize");

        assertEquals(expectedResult, actualResult);

        expectedResult = Gender.getLabels();
        actualResult = service.getStringsByParameter("gender");

        assertEquals(expectedResult, actualResult);

        expectedResult = List.of("true", "false");
        actualResult = service.getStringsByParameter("available");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetIntsByParameter() {
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = products.stream().map(ProductDTO::getPrice).toList();
        List<String> allVolumes = products.stream().map(ProductDTO::getVolume).filter(Objects::nonNull).toList();
        List<String> allVolumesOfBrand = products.stream().filter(p -> p.getBrand().equals("OptyFree"))
                .map(ProductDTO::getVolume).filter(Objects::nonNull).toList();
        List<String> allDiopters = products.stream().filter(p-> p.getNomination().equals("lens"))
                .map(ProductDTO::getSp).toList();
        List<String> allRGDiopters = products.stream().filter(p-> p.getNomination().equals("ready-glasses"))
                .map(ProductDTO::getSp).toList();
        List<String> allCylinders = products.stream().map(ProductDTO::getCyl).filter(Objects::nonNull).toList();
        List<String> allDistances = products.stream().map(ProductDTO::getCyl).filter(Objects::nonNull).toList();
        List<String> allCoefficients = products.stream().map(ProductDTO::getCoefficient)
                .filter(Objects::nonNull).toList();


        when(dtoRepository.getAllPrices()).thenReturn(expectedResult);
        when(dtoRepository.getAllLiquidVolumes()).thenReturn(allVolumes);
        when(dtoRepository.getAllLiquidVolumes(anyString())).thenReturn(allVolumesOfBrand);
        when(dtoRepository.getAllLensDiopters()).thenReturn(allDiopters);
        when(dtoRepository.getAllRGDiopters()).thenReturn(allRGDiopters);
        when(dtoRepository.getAllLensCylinders()).thenReturn(allCylinders);
        when(dtoRepository.getAllDistances()).thenReturn(allDistances);
        when(dtoRepository.getAllCoefficients()).thenReturn(allCoefficients);
        List<String> actualResult = service.getIntsByParameter("price", null);

        assertEquals(expectedResult, actualResult);

        expectedResult = allVolumes;
        actualResult = service.getIntsByParameter("volume", null);

        assertEquals(expectedResult, actualResult);

        expectedResult = allVolumesOfBrand;
        actualResult = service.getIntsByParameter("volume", "OptyFree");

        assertEquals(expectedResult, actualResult);

        expectedResult = allDiopters;
        actualResult = service.getIntsByParameter("sp", null);

        assertEquals(expectedResult, actualResult);

        expectedResult = allRGDiopters;
        actualResult = service.getIntsByParameter("sp", "ready-glasses");

        assertEquals(expectedResult, actualResult);

        expectedResult = allCylinders;
        actualResult = service.getIntsByParameter("cyl", null);

        assertEquals(expectedResult, actualResult);

        expectedResult = allDistances;
        actualResult = service.getIntsByParameter("distance", null);

        assertEquals(expectedResult, actualResult);

        expectedResult = allCoefficients;
        actualResult = service.getIntsByParameter("coeff", null);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllNominations() {
        List<ProductDTO> products = data.getProductDTOList();
        List<String> expectedResult = products.stream().map(ProductDTO::getNomination).toList();

        when(repository.findAllNominations()).thenReturn(expectedResult);
        List<String> actualResult = service.getAllNominations();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetLiquidVolumes() {
        List<String> expectedResult = data.getProductDTOList().stream().map(ProductDTO::getVolume)
                .filter(Objects::nonNull).toList();

        when(dtoRepository.getAllLiquidVolumes(anyString())).thenReturn(expectedResult);
        List<String> actualResult = service.getLiquidVolumes("OptyFree");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetProductForm() {
        String frame = "frame";
        String lens = "lens";
        String readyGlasses = "ready-glasses";
        String liquid = "liquid";
        String medEquip = "medical-equipment";

        Product expectedResult = new Frame();
        Product actualResult = service.getProductForm(frame, null);

        assertEquals(expectedResult.getClass(), actualResult.getClass());

        expectedResult = new Lens();
        actualResult = service.getProductForm(lens, null);

        assertEquals(expectedResult.getClass(), actualResult.getClass());

        expectedResult = new ReadyGlasses();
        actualResult = service.getProductForm(readyGlasses, null);

        assertEquals(expectedResult.getClass(), actualResult.getClass());

        expectedResult = new Liquid();
        actualResult = service.getProductForm(liquid, null);

        assertEquals(expectedResult.getClass(), actualResult.getClass());

        expectedResult = new MedicalEquipment();
        actualResult = service.getProductForm(medEquip, null);

        assertEquals(expectedResult.getClass(), actualResult.getClass());

        expectedResult = new Other();
        actualResult = service.getProductForm("", null);

        assertEquals(expectedResult.getClass(), actualResult.getClass());
    }

    @Test
    void testMakeId() {
        String expectedResult = "TestId";

        MockedStatic<RandomStringUtils> mockRand = mockStatic(RandomStringUtils.class);

        mockRand.when(() -> RandomStringUtils.randomAlphanumeric(anyInt())).thenReturn(expectedResult);
        when(pictureRepository.existsById(anyString())).thenReturn(false);
        String actualResult = service.makeId("picture");

        assertEquals(expectedResult, actualResult);

        actualResult = service.makeId("product");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsImgExistOrNew() throws IOException {
        MultipartFile img = new MockMultipartFile("file", "mockedFile", "img/png", "MockedFile".getBytes());
        Picture expectedResult = new Picture("TestPicture", "file.png", "img/png", "MockedFile".getBytes());


        when(pictureRepository.existsByName(anyString())).thenReturn(false);
        when(pictureRepository.findPictureByName(anyString())).thenReturn(Optional.of(expectedResult));
        when(pictureRepository.existsByName(anyString())).thenReturn(true);
        Picture actualResult = service.isImgExistOrNew(img);

        assertEquals(expectedResult, actualResult);

        actualResult = service.isImgExistOrNew(img);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testChangePricesByCriteria() {
        List<ProductDTO> productsDto = data.getProductDTOList().stream()
                .filter(p -> StringUtils.trimToNull(p.getBrand()).equals("Mien")).toList();
        List<Product> products = data.getProductList().stream()
                .filter(p -> StringUtils.trimToNull(p.getBrand()).equals("Mien"))
                .peek(p -> p.changePriceByPercent("increase", 50)).toList();

        when(dtoRepository.findAllByCriteria("Mien")).thenReturn(productsDto);
        when(repository.findAllById(List.of("TestDtoFrame1", "TestDtoFrame4"))).thenReturn(products);
        when(repository.saveAllAndFlush(products)).thenReturn(products);

        service.changePricesByCriteria("percent", "increase","Mien", 50);

        verify(repository).saveAllAndFlush(products);

        service.changePricesByCriteria("number", "decrease","Mien", 50);
    }
}