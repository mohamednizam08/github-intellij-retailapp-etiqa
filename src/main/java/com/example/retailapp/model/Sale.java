package com.example.retailapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    @NotNull
    private LocalDate saleDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Product product;

    @Min(1)
    private int salesQuantity;

    @NotNull
    private BigDecimal salesPrice;
}
