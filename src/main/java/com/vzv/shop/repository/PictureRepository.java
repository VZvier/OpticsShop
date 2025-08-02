package com.vzv.shop.repository;

import com.vzv.shop.entity.Picture;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {

    boolean deleteByName(String name);
    Optional<Picture> findPictureByName(String name);
    boolean existsByName(String name);
    boolean existsById(@NotNull String id);

    @Modifying
    @Query(value = "DELETE FROM PICTURES AS p USING GOODS_PICTURES AS gp JOIN pictures p2 on p2.id = gp.picture_id " +
            "WHERE p.Id=:id", nativeQuery = true)
    void deletePictureById(@Param("id") String id);
}
