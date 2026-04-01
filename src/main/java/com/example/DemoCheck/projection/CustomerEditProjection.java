package com.example.DemoCheck.projection;

import com.example.DemoCheck.entity.Customer;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "customerEdit", types = Customer.class)
public interface CustomerEditProjection {

    Integer getCustomerNumber();
    String getCustomerName();

    String getContactFirstName();
    String getContactLastName();

    String getPhone();

    String getAddressLine1();
    String getAddressLine2();

    String getCity();
    String getCountry();

    BigDecimal getCreditLimit();
}
