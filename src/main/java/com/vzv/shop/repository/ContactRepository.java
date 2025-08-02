package com.vzv.shop.repository;

import com.vzv.shop.entity.user.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>{

    Optional<Contact> findContactByEmail(String email);
    Optional<Contact> findContactByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query(value = "SELECT c FROM Contact c WHERE c.phone LIKE %?1% OR c.email LIKE %?1% " +
            "OR c.phone = ?1 OR c.email = ?1")
    List<Contact> findContactsByContactSubstring(String string);
}
