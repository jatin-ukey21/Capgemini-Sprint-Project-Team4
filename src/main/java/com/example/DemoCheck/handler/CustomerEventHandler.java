package com.example.DemoCheck.handler;

import com.example.DemoCheck.entity.Customer;
import com.example.DemoCheck.repository.CustomerRepository;
import com.example.DemoCheck.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Customer.class)
public class CustomerEventHandler {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @HandleBeforeCreate
    public void validateCustomer(Customer customer) {
        performValidation(customer);
    }

    @HandleBeforeSave
    public void validateBeforeSave(Customer customer) {
        performValidation(customer);
    }

    public void performValidation(Customer customer) {

        //null check (before repository call)
        if (customer.getCustomerNumber() == null) {
            throw new IllegalArgumentException("customerNumber cannot be null");
        }

        //duplicate customer
        if (customerRepository.existsById(customer.getCustomerNumber())) {
            throw new IllegalArgumentException(
                    "Customer already exists with id: " + customer.getCustomerNumber()
            );
        }

        //customerNumber
        if (customer.getCustomerNumber() <= 0) {
            throw new IllegalArgumentException("customerNumber must be a positive number");
        }

        //customerName
        if (customer.getCustomerName() == null || customer.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("customerName cannot be blank");
        }
        if (customer.getCustomerName().length() > 50) {
            throw new IllegalArgumentException("customerName must be at most 50 characters");
        }

        //contactLastName
        if (customer.getContactLastName() == null || customer.getContactLastName().isBlank()) {
            throw new IllegalArgumentException("contactLastName cannot be blank");
        }

        //contactFirstName
        if (customer.getContactFirstName() == null || customer.getContactFirstName().isBlank()) {
            throw new IllegalArgumentException("contactFirstName cannot be blank");
        }

        //phone
        if (customer.getPhone() == null || !customer.getPhone().matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("phone must be exactly 10 digits");
        }

        //addressLine1
        if (customer.getAddressLine1() == null || customer.getAddressLine1().isBlank()) {
            throw new IllegalArgumentException("addressLine1 cannot be blank");
        }

        //city
        if (customer.getCity() == null || customer.getCity().isBlank()) {
            throw new IllegalArgumentException("city cannot be blank");
        }

        //postalCode (optional but if present must match)
        if (customer.getPostalCode() != null &&
                !customer.getPostalCode().matches("^[0-9]{6}$")) {
            throw new IllegalArgumentException("postalCode must be 6 digits");
        }

        //country
        if (customer.getCountry() == null || customer.getCountry().isBlank()) {
            throw new IllegalArgumentException("country cannot be blank");
        }

        //creditLimit
        if (customer.getCreditLimit() != null &&
                customer.getCreditLimit().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("creditLimit cannot be negative");
        }

        //Employee existence check
        if (customer.getSalesRepEmployee() != null) {

            Integer empId;

            try {
                empId = customer.getSalesRepEmployee().getEmployeeNumber();
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid employee reference");
            }

            if (empId == null || !employeeRepository.existsById(empId)) {
                throw new IllegalArgumentException(
                        "Employee does not exist with id: " + empId
                );
            }
        }
    }
}
