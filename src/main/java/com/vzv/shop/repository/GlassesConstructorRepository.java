package com.vzv.shop.repository;

import com.vzv.shop.entity.constructor.GlassesConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassesConstructorRepository extends JpaRepository<GlassesConstructor, String> {

}
