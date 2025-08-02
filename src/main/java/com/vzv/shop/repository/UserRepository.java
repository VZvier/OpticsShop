package com.vzv.shop.repository;

import com.vzv.shop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findUserByLogin(String login);
    boolean existsByLogin(String login);

    @Query("SELECT u.id FROM User u WHERE u.login LIKE %?1% OR u.id LIKE %?1%")
    List<String> findUsersByLoginOrIdSubstring(String subString);
}
