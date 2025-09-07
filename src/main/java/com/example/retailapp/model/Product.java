package com.example.retailapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotBlank
    private String bookTitle;

    @NotNull
    @Min(0)
    private Integer bookStockQuantity = 0;

    @NotNull
    private BigDecimal bookPrice = BigDecimal.ZERO;
}
