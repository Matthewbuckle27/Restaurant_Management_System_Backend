package com.maveric.restaurantmanagementsystem.controller;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import com.maveric.restaurantmanagementsystem.dto.OrderRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.OrderResponseDTO;
import com.maveric.restaurantmanagementsystem.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(AppConstants.ORDER_MAPPING)
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderDTO) {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(AppConstants.ORDER_FIND_BY_STATUS)
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@PathVariable String status) {
        List<OrderResponseDTO> orderResponseDTOS = orderService.getOrders(status);
        return new ResponseEntity<>(orderResponseDTOS, HttpStatus.OK);
    }

    @GetMapping(AppConstants.ORDER_FIND_BY_USER_ID)
    public ResponseEntity<List<OrderResponseDTO>> getOrdersById(@PathVariable Long userId) {
        List<OrderResponseDTO> orderResponseDTOS = orderService.getOrdersById(userId);
        return new ResponseEntity<>(orderResponseDTOS, HttpStatus.OK);
    }


}
