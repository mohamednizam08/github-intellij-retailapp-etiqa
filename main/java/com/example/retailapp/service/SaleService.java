package com.example.retailapp.service;

import com.example.retailapp.model.Product;
import com.example.retailapp.model.Sale;
import com.example.retailapp.repository.ProductRepository;
import com.example.retailapp.repository.SaleRepository;
import com.example.retailapp.web.BadRequestException;
import com.example.retailapp.web.NotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleService {
    private final SaleRepository saleRepo;
    private final ProductRepository productRepo;

    public SaleService(SaleRepository saleRepo, ProductRepository productRepo) {
        this.saleRepo = saleRepo;
        this.productRepo = productRepo;
    }

    public List<Sale> findAll(){ return saleRepo.findAll(); }
    public Sale findById(Long id){ return saleRepo.findById(id).orElseThrow(() -> new NotFoundException("Sale not found")); }

    public Sale create(Sale s){
        Product product = productRepo.findById(s.getProduct().getBookId()).orElseThrow(() -> new NotFoundException("Product not found"));
        if (product.getBookStockQuantity() < s.getSalesQuantity()) {
            throw new BadRequestException("Insufficient stock");
        }
        product.setBookStockQuantity(product.getBookStockQuantity() - s.getSalesQuantity());
        productRepo.save(product);

        if (s.getSalesPrice() == null || s.getSalesPrice().compareTo(BigDecimal.ZERO) <= 0) {
            s.setSalesPrice(product.getBookPrice());
        }
        return saleRepo.save(s);
    }

    public Sale update(Long id, Sale s){
        Sale existing = findById(id);
        existing.setSaleDate(s.getSaleDate());
        if (s.getProduct() != null && s.getProduct().getBookId() != null) {
            Product p = productRepo.findById(s.getProduct().getBookId()).orElseThrow(() -> new NotFoundException("Product not found"));
            existing.setProduct(p);
        }
        existing.setSalesQuantity(s.getSalesQuantity());
        existing.setSalesPrice(s.getSalesPrice());
        return saleRepo.save(existing);
    }

    public void delete(Long id){ saleRepo.deleteById(id); }

    public byte[] generateSalesReport(LocalDate from, LocalDate to){
        try {
            List<Sale> data = saleRepo.findBySaleDateBetween(from, to);
            InputStream jrxml = new ClassPathResource("reports/sales_report.jrxml").getInputStream();
            JasperReport report = JasperCompileManager.compileReport(jrxml);
            Map<String, Object> params = new HashMap<>();
            params.put("FROM_DATE", java.sql.Date.valueOf(from));
            params.put("TO_DATE", java.sql.Date.valueOf(to));
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
            JasperPrint print = JasperFillManager.fillReport(report, params, ds);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new RuntimeException("Report generation failed: " + e.getMessage(), e);
        }
    }
}
