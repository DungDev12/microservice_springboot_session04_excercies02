package com.exam.product.dto.request;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        BigDecimal price,
        Integer stockQuantity
) {
}
