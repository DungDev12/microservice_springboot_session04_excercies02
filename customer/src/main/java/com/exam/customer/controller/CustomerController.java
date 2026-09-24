package com.exam.customer.controller;

import com.exam.customer.dto.request.CustomerRequestDTO;
import com.exam.customer.dto.request.LoginRequestDTO;
import com.exam.customer.dto.response.CustomerResponseDTO;
import com.exam.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO register(
             @RequestBody CustomerRequestDTO request
    ) {

        return customerService.register(request);
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getById(
            @PathVariable Long id
    ) {

        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerResponseDTO> getAll() {

        return customerService.getAll();
    }

    @PutMapping("/login")
    public CustomerResponseDTO login(
            @RequestBody LoginRequestDTO request
    ) {

        return customerService.login(request);
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO update(
            @PathVariable Long id,
            @RequestBody CustomerRequestDTO request
    ) {

        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {

        customerService.delete(id);
    }
}
