//package com.example.DemoCheck.service;
//
//import com.example.DemoCheck.dto.responsedto.CustomerResponseDTO;
//import com.example.DemoCheck.entity.Customer;
//import com.example.DemoCheck.mapper.CustomerMapper;
//import com.example.DemoCheck.repository.CustomerRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//    private final CustomerRepository customerRepository;
//
//    public CustomerServiceImpl(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
//    @Override
//    public Page<CustomerResponseDTO> searchCustomers(String query, Pageable pageable) {
//
//        Page<Customer> result;
//
//        //Handle empty / null findCustomers → return all customers
//        if (query == null || query.trim().isEmpty()) {
//            result = customerRepository.findAll(pageable);
//        } else {
//            result = customerRepository.searchCustomers(query, pageable);
//        }
//
//        //Map Entity → DTO
//        return result.map(CustomerMapper::toDTO);
//    }
//}