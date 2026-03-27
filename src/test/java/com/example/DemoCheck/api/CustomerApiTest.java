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

    //Helper method
    private Customer createCustomer(int id, String name, String city) {
        Customer c = new Customer();
        c.setCustomerNumber(id);
        c.setCustomerName(name);
        c.setContactLastName("Smith");
        c.setContactFirstName("John");
        c.setPhone("1234567890");
        c.setAddressLine1("Addr1");
        c.setAddressLine2(null);
        c.setCity(city);
        c.setState("MH");
        c.setPostalCode("411001");
        c.setCountry("India");
        c.setCreditLimit(new BigDecimal("10000"));
        c.setSalesRepEmployee(null);
        return c;
    }

    @Test
    void testGetCustomerById() throws Exception {

        // Arrange
        int baseId = (int)(System.currentTimeMillis() % 100000);

        Customer c = createCustomer(baseId, "Single Customer", "Mumbai");
        customerRepository.save(c);

        var request = get("/customer/" + baseId);

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Single Customer"));
    }

    @Test
    void testGetCustomerByInvalidId() throws Exception {

        // Arrange
        int invalidId = 99999999;

        var request = get("/customer/" + invalidId);

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isNotFound());;
    }

    @Test
    void testGetCustomerWithInvalidIdFormat() throws Exception {

        // Arrange
        var request = get("/customer/abc");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isBadRequest());
    }


    @Test
    void testGetAllCustomers() throws Exception {

        // Arrange
        int baseId = (int)(System.currentTimeMillis() % 100000);

        Customer c1 = createCustomer(baseId, "ABC Corp", "Pune");

        customerRepository.save(c1);

        var request = get("/customer");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers[*].customerName")
                        .value(org.hamcrest.Matchers.hasItem("ABC Corp")));
    }

    @Test
    void testPaginationApi() throws Exception {

        // Arrange
        int pageSize = 12;
        var request = get("/customer")
                .param("page", "0")
                .param("size", String.valueOf(pageSize));

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.page.size").value(pageSize))
                .andExpect(jsonPath("$.page.totalElements").exists())
                .andExpect(jsonPath("$._embedded.customers").isArray());
    }

    @Test
    void testPaginationOutOfBounds() throws Exception {

        // Arrange
        var request = get("/customer")
                .param("page", "1000")
                .param("size", "12");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers").isEmpty());
    }

    @Test
    void testProjectionFields() throws Exception {

        // Arrange
        int baseId = (int)(System.currentTimeMillis() % 100000);

        Customer c1 = createCustomer(baseId, "Proj Corp", "Pune");

        customerRepository.save(c1);

        var request = get("/customer")
                .param("projection", "customerView")
                .param("page", "0")
                .param("size", "1");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers[0].contactName").exists())
                .andExpect(jsonPath("$._embedded.customers[0].address").exists());
    }

    @Test
    void testGetCustomerByIdWithProjection() throws Exception {

        // Arrange
        int baseId = (int)(System.currentTimeMillis() % 100000);

        Customer c = createCustomer(baseId, "Projected Customer", "Delhi");
        customerRepository.save(c);

        var request = get("/customer/" + baseId)
                .param("projection", "customerView");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName").exists())
                .andExpect(jsonPath("$.address").exists());
    }

    @Test
    void testSearchApi() throws Exception {

        // Arrange
        int baseId = (int)(System.currentTimeMillis() % 100000);

        Customer c1 = createCustomer(baseId, "SearchTest Corp", "Pune");

        customerRepository.save(c1);

        var request = get("/customer/search/findCustomers")
                .param("keyword", "SearchTest")
                .param("page", "0")
                .param("size", "12");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers[*].customerName")
                        .value(org.hamcrest.Matchers.hasItem("SearchTest Corp")));
    }

    @Test
    void testSearchNoResults() throws Exception {

        // Arrange
        var request = get("/customer/search/findCustomers")
                .param("keyword", "XYZ_NOT_FOUND")
                .param("page", "0")
                .param("size", "12");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers").isEmpty());
    }



    @Test
    void testSearchWithEmptyKeyword() throws Exception {

        // Arrange
        var request = get("/customer/search/findCustomers")
                .param("keyword", "")
                .param("page", "0")
                .param("size", "12");

        // Act
        var result = mockMvc.perform(request);

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers").isArray());
    }
}
