package com.vzv.shop.repository;

import com.vzv.shop.dto.PictureDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureDtoRepository extends JpaRepository<PictureDto, String> {

    @Query(value = "SELECT p.* FROM goods_pictures AS gp \nLEFT JOIN  pictures AS p ON p.id = gp.picture_id\n" +
            "LEFT JOIN goods AS g on g.id = gp.product_id \nWhere g.id=:productId", nativeQuery = true)
    List<PictureDto> findPicturesByProductId(@Param("productId") String productId);
}
