package com.example.DemoCheck.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerPatchDTO {

    // NON-NULLABLE (no presence tracking needed)
    private String customerName;
    private String contactFirstName;
    private String contactLastName;
    private String phone;
    private String addressLine1;
    private String city;
    private String country;

    // NULLABLE FIELDS (presence tracking required)
    private String addressLine2;
    private boolean addressLine2Present = false;

    private String postalCode;
    private boolean postalCodePresent = false;

    private BigDecimal creditLimit;
    private boolean creditLimitPresent = false;

    private Integer salesRepEmployeeNumber;
    private boolean salesRepEmployeeNumberPresent = false;

    // --- SETTERS WITH PRESENCE TRACKING ---

    @JsonSetter("addressLine2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        this.addressLine2Present = true;
    }

    @JsonSetter("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.postalCodePresent = true;
    }

    @JsonSetter("creditLimit")
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
        this.creditLimitPresent = true;
    }

    @JsonSetter("salesRepEmployeeNumber")
    public void setSalesRepEmployeeNumber(Integer salesRepEmployeeNumber) {
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
        this.salesRepEmployeeNumberPresent = true;
    }

    // --- HELPERS ---

    public boolean hasAddressLine2() {
        return addressLine2Present;
    }

    public boolean hasPostalCode() {
        return postalCodePresent;
    }

    public boolean hasCreditLimit() {
        return creditLimitPresent;
    }

    public boolean hasSalesRepEmployeeNumber() {
        return salesRepEmployeeNumberPresent;
    }
}