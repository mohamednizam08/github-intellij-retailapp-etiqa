package com.example.retailapp.service;

import com.example.retailapp.dto.ProductUploadRow;
import com.example.retailapp.model.Product;
import com.example.retailapp.repository.ProductRepository;
import com.example.retailapp.web.BadRequestException;
import com.example.retailapp.web.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo){ this.repo = repo; }

    @Cacheable("products")
    public List<Product> findAll(){ return repo.findAll(); }

    public Product findById(Long id){
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product p){ return repo.save(p); }

    @CacheEvict(value = "products", allEntries = true)
    public Product update(Long id, Product p){
        Product existing = findById(id);
        existing.setBookTitle(p.getBookTitle());
        existing.setBookPrice(p.getBookPrice());
        existing.setBookStockQuantity(p.getBookStockQuantity());
        return repo.save(existing);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void delete(Long id){ repo.deleteById(id); }

    @CacheEvict(value = "products", allEntries = true)
    public List<Product> uploadDelimitedFile(MultipartFile file){
        if (file == null || file.isEmpty())
            throw new BadRequestException("File is empty");
        List<Product> saved = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                row++;
                if (row == 1 && line.toLowerCase().contains("title")) continue; // skip header
                String[] parts = line.split("||");
                if (parts.length < 3) {
                    throw new BadRequestException("Invalid row at line " + row + ": " + line);
                }
                String title = parts[0].trim();
                BigDecimal price = new BigDecimal(parts[1].trim());
                Integer stock = Integer.parseInt(parts[2].trim());
                Product p = new Product();
                p.setBookTitle(title);
                p.setBookPrice(price);
                p.setBookStockQuantity(stock);
                saved.add(repo.save(p));
            }
        } catch (IOException e) {
            throw new BadRequestException("Could not read file: " + e.getMessage());
        }
        return saved;
    }
}
