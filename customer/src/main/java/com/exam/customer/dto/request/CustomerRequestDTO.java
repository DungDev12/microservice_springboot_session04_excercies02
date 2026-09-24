package com.exam.customer.dto.request;

public record CustomerRequestDTO(
        String email,
        String password,
        String fullName
) {
}
