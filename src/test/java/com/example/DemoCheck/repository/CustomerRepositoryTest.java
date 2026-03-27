package com.example.DemoCheck.repository;

import com.example.DemoCheck.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    //Helper method
    private Customer createCustomer(int id, String name, String city) {
        Customer c = new Customer();
        c.setCustomerNumber(id);
        c.setCustomerName(name);
        c.setContactLastName("Last");
        c.setContactFirstName("First");
        c.setPhone("1234567890");
        c.setAddressLine1("Addr1");
        c.setAddressLine2(null);
        c.setCity(city);
        c.setState("MH");
        c.setPostalCode("440001");
        c.setCountry("India");
        c.setCreditLimit(new BigDecimal("10000"));
        return c;
    }

    @Test
    void testFindAllCustomers() {

        // Arrange
        int initialSize = customerRepository.findAll().size();

        int baseId = initialSize + 1000;

        Customer c1 = createCustomer(baseId, "ABC Corp", "Mumbai");
        Customer c2 = createCustomer(baseId + 1, "XYZ Ltd", "Delhi");

        customerRepository.saveAll(List.of(c1, c2));

        // Act
        List<Customer> customers = customerRepository.findAll();

        // Assert
        assertThat(customers.size()).isEqualTo(initialSize + 2);

        assertThat(customers)
                .extracting(Customer::getCustomerName)
                .contains("ABC Corp", "XYZ Ltd");
    }

    @Test
    void testFindCustomersByKeyword() {

        //arrange
        int baseId = customerRepository.findAll().size() + 2000;

        Customer c1 = createCustomer(baseId, "Tech Corp", "Mumbai");
        Customer c2 = createCustomer(baseId + 1, "Retail Inc", "Delhi");

        customerRepository.saveAll(List.of(c1, c2));

        //act
        var result = customerRepository.findCustomers("Tech", org.springframework.data.domain.PageRequest.of(0, 10));

        //assert
        assertThat(result.getContent())
                .extracting(Customer::getCustomerName)
                .contains("Tech Corp");
    }

    @Test
    void testFindCustomersCaseInsensitive() {

        //arrange
        int baseId = customerRepository.findAll().size() + 3000;

        Customer c1 = createCustomer(baseId, "Global Tech", "Mumbai");
        customerRepository.save(c1);

        //act
        var result = customerRepository.findCustomers("tech", org.springframework.data.domain.PageRequest.of(0, 10));

        //assert
        assertThat(result.getContent())
                .extracting(Customer::getCustomerName)
                .contains("Global Tech");
    }

    @Test
    void testPaginationWorks() {

        // Arrange
        int baseId = customerRepository.findAll().size() + 4000;

        for (int i = 0; i < 10; i++) {
            Customer c = createCustomer(baseId + i, "Customer " + i, "City");
            customerRepository.save(c);
        }

        int pageSize = 5;

        // Act
        var page = customerRepository.findAll(
                org.springframework.data.domain.PageRequest.of(0, pageSize));

        // Assert
        assertThat(page).isNotNull();
        assertThat(page.getContent().size()).isEqualTo(pageSize);
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(10);
        assertThat(page.getTotalPages()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindCustomersReturnsEmptyWhenNoMatch() {

        // Act
        var result = customerRepository.findCustomers("NON_EXISTENT",
                org.springframework.data.domain.PageRequest.of(0, 10));

        // Assert
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void testSecondPageHasData() {

        // Arrange
        int pageSize = 5;

        // Act
        var page = customerRepository.findAll(
                org.springframework.data.domain.PageRequest.of(1, pageSize));

        // Assert
        assertThat(page.getNumber()).isEqualTo(1);
    }

    @Test
    void testPaginationMetadata() {

        // Arrange
        int baseId = customerRepository.findAll().size() + 5000;

        int totalRecords = 8;
        int pageSize = 4;

        for (int i = 0; i < totalRecords; i++) {
            Customer c = createCustomer(baseId + i, "Meta " + i, "City");
            customerRepository.save(c);
        }

        // Act
        var page = customerRepository.findAll(
                org.springframework.data.domain.PageRequest.of(0, pageSize));

        // Assert
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(totalRecords);
        assertThat(page.getTotalPages()).isGreaterThanOrEqualTo(2);
        assertThat(page.getSize()).isEqualTo(pageSize);
    }
}


