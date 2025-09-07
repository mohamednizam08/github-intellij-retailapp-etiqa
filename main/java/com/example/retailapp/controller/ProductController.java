package com.example.retailapp.controller;

import com.example.retailapp.model.Product;
import com.example.retailapp.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service){ this.service = service; }

    @GetMapping public List<Product> all(){ return service.findAll(); }
    @GetMapping("/{id}") public Product byId(@PathVariable Long id){ return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Product create(@Valid @RequestBody Product p){ return service.create(p); }
    @PutMapping("/{id}") public Product update(@PathVariable Long id, @Valid @RequestBody Product p){ return service.update(id, p); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){ service.delete(id); }

    @PostMapping(value="/upload", consumes = {"multipart/form-data"})
    public List<Product> upload(@RequestPart("file") MultipartFile file){
        return service.uploadDelimitedFile(file);
    }
}
