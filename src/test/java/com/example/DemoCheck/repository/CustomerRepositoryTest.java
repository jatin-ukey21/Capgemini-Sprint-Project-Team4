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

    @Test
    void testFindAllCustomers() {

        int initialSize = customerRepository.findAll().size();
        int baseId = initialSize + 1000;

        Customer c1 = new Customer(baseId, "ABC Corp", "Smith", "John",
                "1234567890", "Addr1", null, "Mumbai",
                "India", new BigDecimal("10000"));

        Customer c2 = new Customer(baseId + 1, "XYZ Ltd", "Doe", "Jane",
                "9876543210", "Addr2", null, "Delhi",
                "India", new BigDecimal("20000"));

        customerRepository.saveAll(List.of(c1, c2));

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.size()).isEqualTo(initialSize + 2);

        assertThat(customers)
                .extracting(Customer::getCustomerName)
                .contains("ABC Corp", "XYZ Ltd");
    }
}


//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class CustomerRepositoryTest {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    // -------------------------------
//    //  BASIC PAGINATION
//    // -------------------------------
//
//    @Test
//    void shouldFetchFirstPageWithFiveCustomers() {
//
//        Page<Customer> page = customerRepository.findAll(PageRequest.of(0, 5));
//
//        assertNotNull(page);
//        assertEquals(5, page.getContent().size());
//    }
//
//    // -------------------------------
//    //  SECOND PAGE
//    // -------------------------------
//
//    @Test
//    void shouldFetchSecondPage() {
//
//        Page<Customer> page = customerRepository.findAll(PageRequest.of(1, 5));
//
//        assertNotNull(page);
//    }
//
//    // -------------------------------
//    //  OUT OF RANGE
//    // -------------------------------
//
//    @Test
//    void shouldReturnEmptyPageWhenOutOfBounds() {
//
//        Page<Customer> page = customerRepository.findAll(PageRequest.of(100, 5));
//
//        assertTrue(page.isEmpty());
//    }
//
//    // -------------------------------
//    //  METADATA TEST
//    // -------------------------------
//
//    @Test
//    void shouldReturnCorrectPaginationMetadata() {
//
//        Page<Customer> page = customerRepository.findAll(PageRequest.of(0, 5));
//
//        assertTrue(page.getTotalElements() > 0);
//        assertTrue(page.getTotalPages() >= 1);
//    }
//}
