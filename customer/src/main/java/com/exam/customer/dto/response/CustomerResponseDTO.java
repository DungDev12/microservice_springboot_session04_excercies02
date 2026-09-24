package com.exam.customer.dto.response;

public record CustomerResponseDTO(
        Long id,
        String fullName,
        String email
) {
}
