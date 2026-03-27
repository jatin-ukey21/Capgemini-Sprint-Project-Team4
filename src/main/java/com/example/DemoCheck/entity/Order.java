package com.example.DemoCheck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "orderNumber")
    private Integer orderNumber;

    @Column(name = "orderDate", nullable = false)
    private LocalDate orderDate;

    @Column(name = "requiredDate", nullable = false)
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
}

