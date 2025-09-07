package com.example.retailapp.dto;

import java.math.BigDecimal;

public record ProductUploadRow(String title, BigDecimal price, Integer stock) {}
