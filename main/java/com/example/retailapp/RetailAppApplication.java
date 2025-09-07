package com.example.retailapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RetailAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(RetailAppApplication.class, args);
    }
}
