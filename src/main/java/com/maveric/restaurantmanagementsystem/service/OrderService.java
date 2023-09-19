package com.maveric.restaurantmanagementsystem.service;

import com.maveric.restaurantmanagementsystem.dto.OrderRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    List<OrderResponseDTO> getOrders(String status);

    List<OrderResponseDTO> getOrdersById(Long userId);
}
