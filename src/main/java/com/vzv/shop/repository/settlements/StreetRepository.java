package com.vzv.shop.repository.settlements;

import com.vzv.shop.entity.sattlemants.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends JpaRepository<Street, String> {
}
