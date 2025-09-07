package com.example.retailapp.controller;

import com.example.retailapp.model.Sale;
import com.example.retailapp.service.SaleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales")
public class SaleController {
    private final SaleService service;
    public SaleController(SaleService service){ this.service = service; }

    @GetMapping public List<Sale> all(){ return service.findAll(); }
    @GetMapping("/{id}") public Sale byId(@PathVariable Long id){ return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Sale create(@Valid @RequestBody Sale s){ return service.create(s); }
    @PutMapping("/{id}") public Sale update(@PathVariable Long id, @Valid @RequestBody Sale s){ return service.update(id, s); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){ service.delete(id); }
}
