package com.vzv.shop.repository;

import com.vzv.shop.entity.user.Customer;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{

    @Query(value = """
            SELECT c FROM Customer c 
            JOIN FETCH c.contact
            JOIN FETCH c.user
            JOIN FETCH c.address
            """)
    List<Customer> getAllCustomers();

    @Query(value = "SELECT c FROM Customer c WHERE c.fName LIKE %?1% OR c.lName LIKE %?1%")
    List<Customer> findAllByNameSubstring(String name);

    @Query(value = "SELECT c FROM Customer c WHERE c.fName LIKE %?1% AND c.lName LIKE %?2% " +
            "OR c.lName LIKE %?1% AND c.fName LIKE %?2%")
    List<Customer> findAllByName(String lName, String fName);

    @Query(value = "SELECT c FROM Customer c JOIN FETCH User u on u.id = c.id JOIN FETCH Contact cont on c.id = cont.id " +
            "JOIN FETCH Address a on a.id = c.id " +
            "WHERE c.lName LIKE %?1% AND c.fName LIKE %?2% AND c.fatherName LIKE %?3% " +
            "OR c.fName LIKE %?1% AND c.lName LIKE %?2% AND c.fatherName LIKE %?3% " +
            "OR c.fName = ?1 AND c.lName = ?2 AND c.fatherName = ?3 " +
            "OR c.lName = ?1 AND c.fName = ?2 AND c.fatherName = ?3 ")
    List<Customer> findAllByName(String lName, String fName, String fatherName);

    @Query(value = """
            SELECT c FROM Customer c 
            JOIN FETCH c.contact cont 
            JOIN FETCH c.user u
            JOIN FETCH c.address a
            WHERE u.login = ?1 OR u.login LIKE ?1 OR u.login LIKE %?1 OR u.login LIKE ?1% OR u.login LIKE %?1%
            OR cont.phone = ?1 OR cont.phone LIKE ?1 OR cont.phone LIKE %?1 OR cont.phone LIKE ?1% OR cont.phone LIKE %?1%
            OR cont.email = ?1 OR cont.email LIKE ?1 OR cont.email LIKE %?1 OR cont.email LIKE ?1% OR cont.email LIKE %?1%
            """)
    List<Customer> searchByCriteria(@PathParam("criteria") String criteria);

    @Query("""
            SELECT c FROM Customer c LEFT
            JOIN FETCH c.user LEFT JOIN FETCH c.address LEFT JOIN FETCH c.contact
            WHERE (c.fName = ?1 OR c.fName LIKE %?1 OR c.fName LIKE %?1% OR c.fName LIKE ?1% OR c.fName = ?2 OR c.fName LIKE %?2 OR c.fName LIKE %?2% OR c.fName LIKE ?2%)
            AND (c.lName = ?1 OR c.lName LIKE %?1 OR c.lName LIKE %?1% OR c.lName LIKE ?1% OR c.lName = ?3 OR c.lName LIKE ?3 OR c.lName LIKE %?3 OR c.lName LIKE %?3% OR c.lName LIKE ?3%)
            AND (c.fatherName = ?2 OR c.fatherName LIKE %?2 OR c.fatherName LIKE %?2% OR c.fatherName LIKE ?2% OR c.fatherName = ?3 OR c.fatherName LIKE %?2 OR c.fatherName LIKE %?2%  OR c.fatherName LIKE ?2%)
            """)
    List<Customer> findByFullName(String name1, String name2, String name3);

    @Query("""
            SELECT c FROM Customer c
            JOIN FETCH c.user u
            JOIN FETCH c.contact cont
            WHERE u.login = ?1 OR u.login LIKE %?1%
                OR cont.phone = ?1 OR cont.phone LIKE %?1%
                OR cont.email = ?1 OR cont.email LIKE %?1%
                OR c.fName = ?1 OR c.fName LIKE %?1%
                OR c.lName = ?1 OR c.lName LIKE %?1%
                OR c.fatherName = ?1 OR c.fatherName LIKE %?1%
                
            """)
    List<Customer> findMatches(String parameter);
}
