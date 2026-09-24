package com.exam.product.service;

import com.exam.product.dto.request.ProductRequestDTO;
import com.exam.product.dto.response.ProductResponseDTO;
import com.exam.product.entity.Product;
import com.exam.product.exception.ResourceNotFoundException;
import com.exam.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO create(
            ProductRequestDTO request
    ) {

        Product product = Product.builder()
                .name(request.name().trim())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .build();

        Product savedProduct =
                productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Transactional
    public ProductResponseDTO getById(Long id) {

        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy sản phẩm với id: " + id
                        )
                );

        return mapToResponse(product);
    }

    @Transactional
    public List<ProductResponseDTO> getAll() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponseDTO mapToResponse(
            Product product
    ) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
