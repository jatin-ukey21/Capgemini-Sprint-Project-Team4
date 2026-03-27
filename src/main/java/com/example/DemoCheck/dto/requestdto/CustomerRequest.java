package com.example.DemoCheck.dto.requestdto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CustomerRequest(

        @NotNull @Positive
        Integer customerNumber,

        @NotBlank @Size(max = 50)
        String customerName,

        @NotBlank
        String contactLastName,

        @NotBlank
        String contactFirstName,

        @NotBlank
        @Pattern(regexp = "^[0-9]{10}$")
        String phone,

        @NotBlank
        String addressLine1,

        String addressLine2,

        @NotBlank
        String city,

        String state,

        @Pattern(regexp = "^[0-9]{6}$")
        String postalCode,

        @NotBlank
        String country,

        @DecimalMin("0.0")
        BigDecimal creditLimit,

        Integer salesRepEmployeeNumber
) {}