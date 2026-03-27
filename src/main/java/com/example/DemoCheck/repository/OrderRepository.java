package com.example.DemoCheck.repository;
import com.example.DemoCheck.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;

@RepositoryRestResource(path = "orders")
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByStatus(String status, Pageable pageable);

    Page<Order> findByOrderDate(LocalDate orderDate, Pageable pageable);

    Page<Order> findByOrderDateBetween(LocalDate start, LocalDate end, Pageable pageable);
}