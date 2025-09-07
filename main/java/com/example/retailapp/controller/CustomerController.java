package com.example.retailapp.controller;

import com.example.retailapp.model.Customer;
import com.example.retailapp.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service){ this.service = service; }

    @GetMapping public List<Customer> all(){ return service.findAll(); }
    @GetMapping("/{id}") public Customer byId(@PathVariable Long id){ return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Customer create(@Valid @RequestBody Customer c){ return service.create(c); }
    @PutMapping("/{id}") public Customer update(@PathVariable Long id, @Valid @RequestBody Customer c){ return service.update(id, c); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){ service.delete(id); }
}
