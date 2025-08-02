package com.vzv.shop.repository;

import com.vzv.shop.entity.user.FullUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullUserRepository extends JpaRepository<FullUser, String>{
}
