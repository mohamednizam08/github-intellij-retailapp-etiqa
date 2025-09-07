package com.example.retailapp;

import com.example.retailapp.controller.CustomerController;
import com.example.retailapp.model.Customer;
import com.example.retailapp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
    @Test
    void getAll_ok() throws Exception {
        CustomerService svc = Mockito.mock(CustomerService.class);
        Mockito.when(svc.findAll()).thenReturn(List.of(new Customer()));
        MockMvc mvc = MockMvcBuilders.standaloneSetup(new CustomerController(svc)).build();
        mvc.perform(get("/api/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
