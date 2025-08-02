package com.vzv.shop.service;

import com.vzv.shop.entity.user.Address;
import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<User> getAll();

    List<Customer> getAllCustomers();

    User getById(String id);

    Customer getCustomerById(String id);

    String isEncoded(String pass, String encoded);

    List<Customer> getCustomersByLoginOrIdSubstring(String login);

    User getByLogin(String login);

    boolean isLoginExists(String login);

    List<Customer> getCustomersByContactSubstring(String email);

    User getByEmail(String email);

    User getByPhone(String phone);

    List<Customer> getCustomersNamesByItsSubstring(String name);

    boolean isEmailExists(String email);

    boolean isPhoneExists(String number);

    User save(User user);

    Customer saveContact(Contact contact);

    Customer saveCustomer(Customer customer);

    Customer save(User user, Customer customer, Contact contact, Address address);

    User updateUser(User user);

    boolean delete(String id);

    boolean deleteCustomer(String id);

    UserDetails convertUserToUserDetails(User user);

    String generateId();

    String encodePass(String pass);

    List<Customer> findCustomerByCriteria(String criteria);

    List<String> findCustomerData(String criteria);

    String reformatPhone(String phoneNumber);

    String reformatEmail(String email);
}