package com.example.retailapp.service;

import com.example.retailapp.model.Customer;
import com.example.retailapp.repository.CustomerRepository;
import com.example.retailapp.web.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;
    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    public List<Customer> findAll(){ return repo.findAll(); }
    public Customer findById(Long id){ return repo.findById(id).orElseThrow(() -> new NotFoundException("Customer not found")); }
    public Customer create(Customer c){ return repo.save(c); }
    public Customer update(Long id, Customer c){
        Customer existing = findById(id);
        existing.setFirstName(c.getFirstName());
        existing.setLastName(c.getLastName());
        existing.setOfficeEmail(c.getOfficeEmail());
        existing.setPersonalEmail(c.getPersonalEmail());
        return repo.save(existing);
    }
    public void delete(Long id){ repo.deleteById(id); }
}
