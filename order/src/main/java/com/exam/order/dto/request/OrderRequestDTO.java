package com.exam.order.dto.request;

public record OrderRequestDTO(
        Long customerId,
        Long productId,
        Integer quantity
) {
}
