package com.example.DemoCheck.api;

import com.example.DemoCheck.entity.Customer;
import com.example.DemoCheck.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testGetAllCustomers() throws Exception {

        // Arrange
        Customer c1 = new Customer(9999, "ABC Corp", "Smith", "John",
                "1234567890", "Addr1", null, "Mumbai",
                "India", new BigDecimal("10000"));

        customerRepository.save(c1);

        // Act + Assert
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers[*].customerName")
                        .value(org.hamcrest.Matchers.hasItem("ABC Corp")));
    }
}
//@SpringBootTest
//@AutoConfigureMockMvc
//class CustomerApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    // -------------------------------
//    // DEFAULT PAGINATION
//    // -------------------------------
//
//    @Test
//    void shouldReturnCustomersWithDefaultPagination() throws Exception {
//
//        mockMvc.perform(get("/customers"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray());
//    }
//
//    // -------------------------------
//    // FIRST PAGE
//    // -------------------------------
//
//    @Test
//    void shouldReturnFirstPageWithFiveRecords() throws Exception {
//
//        mockMvc.perform(get("/customers")
//                        .param("page", "0")
//                        .param("size", "5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value(5));
//    }
//
//    // -------------------------------
//    // SECOND PAGE
//    // -------------------------------
//
//    @Test
//    void shouldReturnSecondPage() throws Exception {
//
//        mockMvc.perform(get("/customers")
//                        .param("page", "1")
//                        .param("size", "5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray());
//    }
//
//    // -------------------------------
//    // OUT OF RANGE PAGE
//    // -------------------------------
//
//    @Test
//    void shouldReturnEmptyWhenPageOutOfBounds() throws Exception {
//
//        mockMvc.perform(get("/customers")
//                        .param("page", "100")
//                        .param("size", "5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isEmpty());
//    }
//}