package com.vzv.shop.service.implementation;

import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.entity.user.Address;
import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.enumerated.Role;
import com.vzv.shop.repository.ContactRepository;
import com.vzv.shop.repository.CustomerRepository;
import com.vzv.shop.repository.UserRepository;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ContactRepository contactRepository;

    public UserServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           ContactRepository contactRepository,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
        this.encoder = encoder;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public User getByLogin(String login) {
        User user = userRepository.findUserByLogin(login).orElse(null);
        if (user == null) {
            log.warn("User {} not found!", login);
        }
        return user;
    }

    @Override
    public boolean isLoginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public User getByEmail(String email) {
        Contact contact = contactRepository.findContactByEmail(email).orElse(null);
        if (contact != null) {
            return userRepository.findById(contact.getId().trim()).orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public User getByPhone(String phone) {
        Contact contact = contactRepository.findContactByPhone(phone).orElse(null);
        if (contact != null) {
            return userRepository.findById(contact.getId()).orElse(null);
        }
        return null;
    }

    @Override
    public boolean isEmailExists(String email) {
        return contactRepository.existsByEmail(email);
    }

    @Override
    public boolean isPhoneExists(String number) {
        return contactRepository.existsByPhone(number);
    }

    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Customer saveContact(Contact contact) {
        contactRepository.saveAndFlush(contact);
        return customerRepository.findById(contact.getId()).orElse(null);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        User customerUser = customer.getUser();
        customerUser.setPassword(encodeIfNotEncoded(customerUser.getPassword()));
        customerRepository.save(customer);
        return customerRepository.findById(customer.getId()).orElse(null);
    }

    @Override
    public Customer save(User user, Customer customer, Contact contact, Address address) {
        user.setPassword(encodeIfNotEncoded(user.getPassword()));
        customer.setUser(user);
        customer.setContact(contact);
        customer.setAddress(address);
        log.info("Try to save entity: {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public User updateUser(User user) {
        User dbUser = userRepository.findById(user.getId()).orElse(null);
        if (dbUser != null) {
            if(user.getPassword().length() < 60) {
                user.setPassword(encodeIfNotEncoded(user.getPassword()));
            }
            BeanUtils.copyProperties(user, dbUser);
            log.info("Try to save entity: {}", dbUser);
            return userRepository.save(dbUser);
        } else {
            log.error("User {} not found", user.getId());
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).orElse(null) == null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        customerRepository.deleteById(id);
        return customerRepository.findById(id).orElse(null) == null;
    }

    public UserDetails convertUserToUserDetails(User user) {
        if (user != null) {
            log.info("User role is: {}", user.getRoles());
        }
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getLogin().strip())
                    .password(user.getPassword().strip())
                    .authorities(user.getRoles().stream().map(Role::getLabel).toArray(String[]::new))
                    .build();
        } else {
            log.error("User is null");
            return null;
        }
    }

    @Override
    public String generateId() {
        String id = AuxiliaryUtils.makeRandomString(12);
        while (customerRepository.existsById(id)) {
            id = AuxiliaryUtils.makeRandomString(12);
        }
        return id;
    }

    @Override
    public String encodePass(String pass) {
        return encoder.encode(pass);
    }

    @Override
    public String isEncoded(String pass, String encoded) {
        if (encoder.matches(pass, encoded)){
            return "Match original!";
        } else if (encoder.matches(pass, encoded.strip())){
            return "Match encoded!";
        } else {
            return "!Match!";
        }
    }

    private String encodeIfNotEncoded(String pass) {
        String result = "";
        if (pass.length() <= 60) {
            result = encodePass(pass);
        } else {
            result = pass;
        }
        log.info("Return Pass: {}!", pass);
        return result;
    }

    @Override
    public List<Customer> getCustomersByLoginOrIdSubstring(String login) {
        List<String> userIds = userRepository.findUsersByLoginOrIdSubstring(login)
                .stream().map(String::strip).toList();
        return customerRepository.findAllById(userIds);
    }

    @Override
    public List<Customer> getCustomersByContactSubstring(String email) {
        List<String> contactsIds = contactRepository.findContactsByContactSubstring(email)
                .stream().map(c -> c.getId().strip()).toList();
        return customerRepository.findAllById(contactsIds);
    }

    @Override
    public List<Customer> getCustomersNamesByItsSubstring(String name) {
        String[] splitName = name.split(" ");
        List<Customer> customers = new ArrayList<>();
        switch (splitName.length) {
            case (1) -> {
                customers = customerRepository.findAllByNameSubstring(name);
            }
            case (2) -> {
                customers = customerRepository.findAllByName(splitName[0], splitName[1]);
            }
            case (3) -> {
                customers = customerRepository.findAllByName(splitName[0], splitName[1], splitName[2]);
            }
            default -> {
                return new ArrayList<>();
            }
        }
        return customers;
    }

    @Override
    public List<Customer> findCustomerByCriteria(String criteria) {
        List<Customer> result;
        String[] split = criteria.split(" ");
        log.info("Split: " + List.of(split));
        if (split.length == 2) {
            log.info("Split length: {}", split.length);
            result = customerRepository.findAllByName(split[0].strip(), split[1].strip());
        } else if (split.length == 3) {
            log.info("Split length: {}", split.length);
            result = customerRepository.findAllByName(split[0].strip(), split[1].strip(), split[2].strip());
            if (result.isEmpty()){
                result = customerRepository.findByFullName(split[0], split[1], split[2]);
            }
        } else {
            result = customerRepository.searchByCriteria(criteria);
        }
        return result;
    }

    @Override
    public List<String> findCustomerData(String criteria) {
        return customerRepository.findMatches(criteria).stream()
                .map(c -> {
                        if (c.getUser().getLogin().contains(criteria)) {
                            return c.getUser().getLogin().strip();

                        } else if (c.getContact().getPhone().contains(criteria)) {
                            return c.getContact().getPhone().strip();

                        } else if (c.getContact().getEmail().contains(criteria)) {
                            return c.getContact().getEmail().strip();

                        } else {
                            return c.getLName().strip() + " " + c.getFName().strip() + " " + c.getFatherName().strip();
                        }
                    })
                .toList();
    }

    @Override
    public String reformatPhone(String phoneNumber){
        if (phoneNumber != null) {
            List<Integer> indexes = List.of(new Integer[]{0, 2, 5, 8, 10});
            List<String> chars = List.of(new String[]{"+", "(", ")", "-", "-"});
            StringBuilder sb = new StringBuilder();
            List<String> digits = List.of(StringUtils.getDigits(phoneNumber).split(""));
            int counter = 0;
            int i = 0;
            while (counter < 12) {
                if (indexes.size() > i && counter == indexes.get(i)) {
                    sb.append(chars.get(i));
                    i++;
                }
                sb.append(digits.get(counter));
                counter++;
            }
            System.out.println("ReFormat phone number: " + sb.toString() + "!");
            return sb.toString();
        } else {
            return null;
        }
    }

    @Override
    public String reformatEmail(String email){
        if (email != null) {
            email = StringUtils.toRootLowerCase(email);
            System.out.println("ReFormat email: " + email);
            return email;
        } else {
            return null;
        }
    }
}