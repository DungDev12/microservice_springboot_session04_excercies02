package com.exam.order.service;

import com.exam.order.dto.request.OrderRequestDTO;
import com.exam.order.dto.response.OrderResponseDTO;
import com.exam.order.entity.Order;
import com.exam.order.exception.DatabaseException;
import com.exam.order.exception.ResourceNotFoundException;
import com.exam.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDTO createOrder(
            OrderRequestDTO request
    ) {

        BigDecimal productPrice =
                getProductPrice(request.productId());

        BigDecimal totalAmount =
                productPrice.multiply(
                        BigDecimal.valueOf(
                                request.quantity()
                        )
                );

        Order order = Order.builder()
                .customerId(request.customerId())
                .productId(request.productId())
                .orderDate(LocalDate.from(LocalDateTime.now()))
                .totalAmount(totalAmount)
                .build();

        try {
            Order savedOrder =
                    orderRepository.save(order);
            return mapToResponse(savedOrder);

        } catch (DataAccessException ex) {

            throw new DatabaseException(
                    "Không thể lưu đơn hàng vào database",
                    ex
            );
        }
    }

    @Transactional
    public OrderResponseDTO getOrderById(
            Long id
    ) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy order với id: " + id
                        )
                );
        return mapToResponse(order);
    }

    @Transactional
    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BigDecimal getProductPrice(
            Long productId
    ) {
        return new BigDecimal("100000");
    }

    private OrderResponseDTO mapToResponse(
            Order order
    ) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerId(),
                order.getProductId(),
                order.getOrderDate().atStartOfDay(),
                order.getTotalAmount()
        );
    }
}
