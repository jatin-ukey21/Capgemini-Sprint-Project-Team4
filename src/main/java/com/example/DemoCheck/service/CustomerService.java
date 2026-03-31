package com.example.DemoCheck.service;

import com.example.DemoCheck.dto.CustomerPatchDTO;
import com.example.DemoCheck.entity.Customer;
import com.example.DemoCheck.entity.Employee;
import com.example.DemoCheck.repository.CustomerRepository;
import com.example.DemoCheck.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public Customer patchCustomer(Integer id, CustomerPatchDTO dto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // --- NON-NULLABLE FIELDS ---

        if (dto.getCustomerName() != null) {
            validateNotBlank(dto.getCustomerName(), "customerName");
            customer.setCustomerName(dto.getCustomerName().trim());
        }

        if (dto.getContactFirstName() != null) {
            validateNotBlank(dto.getContactFirstName(), "contactFirstName");
            customer.setContactFirstName(dto.getContactFirstName().trim());
        }

        if (dto.getContactLastName() != null) {
            validateNotBlank(dto.getContactLastName(), "contactLastName");
            customer.setContactLastName(dto.getContactLastName().trim());
        }

        if (dto.getPhone() != null) {
            String normalized = normalizePhone(dto.getPhone());

            if (normalized.length() < 8 || normalized.length() > 15) {
                throw new IllegalArgumentException("phone must contain 8 to 15 digits");
            }

            customer.setPhone(normalized);
        }

        if (dto.getAddressLine1() != null) {
            validateNotBlank(dto.getAddressLine1(), "addressLine1");
            customer.setAddressLine1(dto.getAddressLine1().trim());
        }

        if (dto.getCity() != null) {
            validateNotBlank(dto.getCity(), "city");
            customer.setCity(dto.getCity().trim());
        }

        if (dto.getCountry() != null) {
            validateNotBlank(dto.getCountry(), "country");
            customer.setCountry(dto.getCountry().trim());
        }

        // --- NULLABLE FIELDS (PATCH CORRECT LOGIC) ---

        if (dto.hasAddressLine2()) {
            if (dto.getAddressLine2() == null) {
                customer.setAddressLine2(null);
            } else {
                customer.setAddressLine2(dto.getAddressLine2().trim());
            }
        }

        if (dto.hasPostalCode()) {
            if (dto.getPostalCode() == null) {
                customer.setPostalCode(null);
            } else {
                String postal = dto.getPostalCode().trim();

                if (postal.length() < 3 || postal.length() > 10) {
                    throw new IllegalArgumentException("postalCode must be between 3 and 10 characters");
                }

                customer.setPostalCode(postal);
            }
        }

        if (dto.hasCreditLimit()) {
            if (dto.getCreditLimit() == null) {
                customer.setCreditLimit(null);
            } else {
                if (dto.getCreditLimit().signum() < 0) {
                    throw new IllegalArgumentException("creditLimit cannot be negative");
                }
                customer.setCreditLimit(dto.getCreditLimit());
            }
        }

        if (dto.hasSalesRepEmployeeNumber()) {
            if (dto.getSalesRepEmployeeNumber() == null) {
                customer.setSalesRepEmployee(null);
            } else {
                Employee emp = employeeRepository.findById(dto.getSalesRepEmployeeNumber())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid or non-existing employee reference"));
                customer.setSalesRepEmployee(emp);
            }
        }

        return customerRepository.save(customer);
    }

    private String normalizePhone(String phone) {
        return phone.replaceAll("[^\\d]", "");
    }

    private void validateNotBlank(String value, String field) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
    }
}