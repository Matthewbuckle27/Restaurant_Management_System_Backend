package com.maveric.restaurantmanagementsystem.dto;

import com.maveric.restaurantmanagementsystem.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private String orderId;
    private String userName;
    private LocalDateTime orderDate;
    private double totalPrice;
    private OrderStatus status;
}
