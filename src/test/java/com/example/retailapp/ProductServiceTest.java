package com.example.retailapp;

import com.example.retailapp.model.Product;
import com.example.retailapp.repository.ProductRepository;
import com.example.retailapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    @Test
    void uploadDelimitedFile_parsesRows() throws Exception {
        ProductRepository repo = Mockito.mock(ProductRepository.class);
        Mockito.when(repo.save(Mockito.any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        ProductService svc = new ProductService(repo);
        String data = "title|price|stock\nFashion Design|120.00|3\nDDD|130.50|2\n";
        MultipartFile file = new MultipartFile() {
            @Override public String getName(){ return "file"; }
            @Override public String getOriginalFilename(){ return "products.txt"; }
            @Override public String getContentType(){ return "text/plain"; }
            @Override public boolean isEmpty(){ return false; }
            @Override public long getSize(){ return data.length(); }
            @Override public byte[] getBytes(){ return data.getBytes(); }
            @Override public java.io.InputStream getInputStream(){ return new ByteArrayInputStream(data.getBytes()); }
            @Override public void transferTo(java.io.File dest){}
        };
        List<Product> products = svc.uploadDelimitedFile(file);
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getBookTitle()).isEqualTo("Fashion Design");
        assertThat(products.get(1).getBookPrice()).isEqualByComparingTo(new BigDecimal("130.50"));
    }
}
