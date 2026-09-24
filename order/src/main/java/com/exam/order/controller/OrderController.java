package com.exam.order.controller;

import com.exam.order.dto.request.OrderRequestDTO;
import com.exam.order.dto.response.OrderResponseDTO;
import com.exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(
            @RequestBody OrderRequestDTO request
    ) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(
            @PathVariable Long id
    ) {
        return orderService.getOrderById(id);
    }
}
