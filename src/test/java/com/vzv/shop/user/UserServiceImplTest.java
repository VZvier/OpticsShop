package com.vzv.shop.user;

import com.vzv.shop.DataForTests;
import com.vzv.shop.data.AuxiliaryUtils;
import com.vzv.shop.entity.user.Contact;
import com.vzv.shop.entity.user.Customer;
import com.vzv.shop.entity.user.User;
import com.vzv.shop.repository.ContactRepository;
import com.vzv.shop.repository.CustomerRepository;
import com.vzv.shop.repository.UserRepository;
import com.vzv.shop.service.implementation.UserServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private UserServiceImpl userService;


    private final DataForTests data = new DataForTests();

    @Test
    void testGetAll() {
        List<User> expectedResult = data.getUserList();

        when(userRepository.findAll()).thenReturn(expectedResult);
        List<User> actualResult = userService.getAll();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> expectedResult = data.getCustomerList();

        when(customerRepository.getAllCustomers()).thenReturn(expectedResult);
        List<Customer> actualResult = userService.getAllCustomers();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetById() {
        User expectedResult = data.getUserList().get(0);

        when(userRepository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        User actualResult = userService.getById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCustomerById() {
        Customer expectedResult = data.getCustomerList().get(0);

        when(customerRepository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
        Customer actualResult = userService.getCustomerById(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByLogin() {
        User expectedResult = data.getUserList().get(0);

        when(userRepository.findUserByLogin(expectedResult.getLogin())).thenReturn(Optional.of(expectedResult));
        User actualResult = userService.getByLogin(expectedResult.getLogin());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsLoginExists() {
        User user = data.getUserList().get(0);
        boolean expectedResult = true;

        when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);
        boolean actualResult = userService.isLoginExists(user.getLogin());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByEmail() {
        Customer customer = data.getCustomerList().get(0);
        Contact contact = customer.getContact();
        User expectedResult = customer.getUser();

        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(expectedResult));
        when(contactRepository.findContactByEmail(contact.getEmail()))
                .thenReturn(Optional.of(contact));
        User actualResult = userService.getByEmail(contact.getEmail());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetByPhone() {
        Customer customer = data.getCustomerList().get(0);
        Contact contact = customer.getContact();
        User expectedResult = customer.getUser();

        when(contactRepository.findContactByPhone(contact.getPhone())).thenReturn(Optional.of(contact));
        when(userRepository.findById(contact.getId())).thenReturn(Optional.of(expectedResult));
        User actualResult = userService.getByPhone(contact.getPhone());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsEmailExists() {
        Customer customer= data.getCustomerList().get(0);
        boolean expectedResult = true;

        when(contactRepository.existsByEmail(customer.getContact().getEmail()))
                .thenReturn(true);
        boolean actualResult = userService.isEmailExists(customer.getContact().getEmail());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testIsPhoneExists() {
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = true;

        when(contactRepository.existsByPhone(customer.getContact().getPhone()))
                .thenReturn(true);
        boolean actualResult = userService.isPhoneExists(customer.getContact().getPhone());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSave() {
        User expectedResult = data.getUserList().get(0);

        when(encoder.encode(anyString())).thenReturn(expectedResult.getPassword());
        when(userRepository.save(any()))
                .thenReturn(expectedResult);
        User actualResult = userService.save(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveContact() {
        Customer expectedResult = data.getCustomerList().get(0);
        Contact contact = expectedResult.getContact();

        when(contactRepository.saveAndFlush(contact)).thenReturn(contact);
        when(customerRepository.findById(contact.getId())).thenReturn(Optional.of(expectedResult));
        Customer actualResult = userService.saveContact(contact);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveCustomer() {
        Customer expectedResult = data.getCustomerList().get(0);

        when(customerRepository.save(expectedResult))
                .thenReturn(expectedResult);
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(expectedResult));
        Customer actualResult = userService.saveCustomer(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdateUser() {
        User expectedResult = data.getUserList().get(0);

        when(userRepository.findById(expectedResult.getId()))
                .thenReturn(Optional.of(expectedResult));
        when(userRepository.save(expectedResult)).thenReturn(expectedResult);
        User actualResult = userService.updateUser(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDelete() {
        User user = data.getUserList().get(0);
        boolean expectedResult = true;

        doNothing().when(userRepository).deleteById(user.getId());
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        boolean actualResult = userService.delete(user.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = data.getCustomerList().get(0);
        boolean expectedResult = true;

        boolean actualResult = userService.deleteCustomer(customer.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGenerateId() {
        Customer customer = data.getCustomerList().get(0);
        String expectedResult = customer.getId();

        try (MockedStatic<AuxiliaryUtils> randAuxUtils = mockStatic(AuxiliaryUtils.class)) {

            randAuxUtils.when(() -> AuxiliaryUtils.makeRandomString(anyInt())).thenReturn(expectedResult);
            String actualResult = userService.generateId();
            assertEquals(expectedResult, actualResult);
        } catch (Exception e) {
            System.out.println("Error: cannot invoke RandomStringUtils.randomAlphanumeric(anyInt())");
        }
    }

    @Test
    void testGetCustomersByLoginOrIdSubstring() {
        Customer customer = data.getCustomerList().get(0);
        String idSubstr = customer.getId().substring(0,4);
        String loginSubstr = customer.getUser().getLogin().substring(0,4);

        List<Customer> expectedResult = List.of(customer);

        when(userRepository.findUsersByLoginOrIdSubstring(idSubstr)).thenReturn(List.of(customer.getUser().getId()));
        when(userRepository.findUsersByLoginOrIdSubstring(loginSubstr)).thenReturn(List.of(customer.getUser().getId()));
        when(customerRepository.findAllById(anyList())).thenReturn(List.of(customer));
        List<Customer> actualResult = userService.getCustomersByLoginOrIdSubstring(idSubstr);

        assertEquals(expectedResult, actualResult);

        actualResult = userService.getCustomersByLoginOrIdSubstring(loginSubstr);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCustomersByContactSubstring() {
        Customer customer = data.getCustomerList().get(0);
        String emailSubstr = customer.getContact().getEmail().substring(0,4);
        String phoneSubstr = customer.getContact().getPhone().substring(0,4);

        List<Customer> expectedResult = List.of(customer);

        when(contactRepository.findContactsByContactSubstring(emailSubstr)).thenReturn(List.of(customer.getContact()));
        when(customerRepository.findAllById(anyList())).thenReturn(List.of(customer));
        List<Customer> actualResult = userService.getCustomersByContactSubstring(emailSubstr);

        assertEquals(expectedResult, actualResult);

        actualResult = userService.getCustomersByLoginOrIdSubstring(phoneSubstr);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetCustomersNamesByItsSubstring() {
        Customer customer = data.getCustomerList().get(0);
        String lName = customer.getLName();
        String fName = customer.getFName();
        String fatherName = customer.getFatherName();

        List<Customer> expectedResult = List.of(customer);

        when(customerRepository.findAllByNameSubstring(lName)).thenReturn(List.of(customer));
        when(customerRepository.findAllByName(lName, fName)).thenReturn(List.of(customer));
        when(customerRepository.findAllByName(lName, fName, fatherName)).thenReturn(List.of(customer));
        List<Customer> actualResult = userService.getCustomersNamesByItsSubstring(lName);

        assertEquals(expectedResult, actualResult);

        actualResult = userService.getCustomersNamesByItsSubstring(lName + " " + fName);

        assertEquals(expectedResult, actualResult);

        actualResult = userService.getCustomersNamesByItsSubstring(lName + " " + fName + " " + fatherName);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindCustomerByCriteria() {
        Customer customer = data.getCustomerList().get(0);
        List<Customer> expectedResult = List.of(customer);

        when(customerRepository.findAllByName(anyString(), anyString())).thenReturn(List.of(customer));
        when(customerRepository.findAllByName(anyString(), anyString(), anyString())).thenReturn(List.of(customer));
        when(customerRepository.searchByCriteria(anyString())).thenReturn(List.of(customer));

        List<Customer> actualResult = userService.findCustomerByCriteria(customer.getId());

        assertEquals(expectedResult, actualResult);

        actualResult = userService.findCustomerByCriteria(customer.getLName() + " " + customer.getFName());

        assertEquals(expectedResult, actualResult);

        actualResult = userService.findCustomerByCriteria(
                customer.getLName() + " " + customer.getFName() + " " + customer.getFatherName()
        );

        assertEquals(expectedResult, actualResult);

        actualResult = userService.findCustomerByCriteria(customer.getBorn());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFindCustomerData() {
        List<Customer> customers = data.getCustomerList();
        String criteria = customers.get(0).getId().substring(0,4);
        List<String> expectedResult =customers.stream().map(c -> {
            if (c.getUser().getLogin().contains(criteria)) {
                return c.getUser().getLogin().strip();

            } else if (c.getContact().getPhone().contains(criteria)) {
                return c.getContact().getPhone().strip();

            } else if (c.getContact().getEmail().contains(criteria)) {
                return c.getContact().getEmail().strip();

            } else {
                return c.getLName().strip() + " " + c.getFName().strip() + " " + c.getFatherName().strip();
            }
        }).toList();

        when(customerRepository.findMatches(anyString())).thenReturn(customers);

        List<String> actualResult = userService.findCustomerData(criteria);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testReformatPhone() {
        String number = "380466157433";
        String expectedResult = "+38(046)615-74-33";

        String actualResult = userService.reformatPhone(number);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testReformatEmail() {
        String email = "IvanIvaNov@GMail.com";
        String expectedResult = "ivanivanov@gmail.com";

        String actualResult = userService.reformatEmail(email);

        assertEquals(expectedResult, actualResult);
    }
}