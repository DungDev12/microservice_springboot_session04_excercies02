package com.exam.product.controller;

import com.exam.product.dto.request.ProductRequestDTO;
import com.exam.product.dto.response.ProductResponseDTO;
import com.exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO createProduct(
           @RequestBody ProductRequestDTO request
    ) {

        return productService.create(request);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(
            @PathVariable Long id
    ) {

        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAll();
    }
}
