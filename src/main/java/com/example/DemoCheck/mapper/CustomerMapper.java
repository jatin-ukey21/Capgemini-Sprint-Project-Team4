//package com.example.DemoCheck.mapper;
//
//import com.example.DemoCheck.dto.responsedto.CustomerResponseDTO;
//import com.example.DemoCheck.entity.Customer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomerMapper {
//    public static CustomerResponseDTO toDTO(Customer c) {
//
//        return new CustomerResponseDTO(
//                c.getCustomerNumber(),
//                c.getCustomerName(),
//                buildContactName(c.getContactFirstName(), c.getContactLastName()),
//                c.getPhone(),
//                buildAddress(c.getAddressLine1(), c.getAddressLine2()),
//                c.getCity(),
//                c.getCountry(),
//                c.getCreditLimit()
//        );
//    }
//
//    private static String buildContactName(String first, String last) {
//        if (first == null && last == null) return "";
//        if (first == null) return last;
//        if (last == null) return first;
//        return first + " " + last;
//    }
//
//    private static String buildAddress(String a1, String a2) {
//        if (a1 == null && a2 == null) return "";
//        if (a2 == null) return a1;
//        return a1 + ", " + a2;
//    }
//}
