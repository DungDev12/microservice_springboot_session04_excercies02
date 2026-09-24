package com.exam.order.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseError {

    @Builder.Default
    private LocalDateTime timestamp =  LocalDateTime.now();
    private int status;
    private String error;
    private String message;
}
