package com.maveric.restaurantmanagementsystem.controller;

import com.maveric.restaurantmanagementsystem.dto.*;
import com.maveric.restaurantmanagementsystem.entity.Order;
import com.maveric.restaurantmanagementsystem.entity.OrderStatus;
import com.maveric.restaurantmanagementsystem.entity.User;
import com.maveric.restaurantmanagementsystem.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_CreateOrder(){
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        OrderItemDTO orderItemDTO1 = new OrderItemDTO();
        orderItemDTO1.setProductId(1L);
        orderItemDTO1.setQuantity(1);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO();
        orderItemDTO2.setProductId(2L);
        orderItemDTO2.setQuantity(2);
        List<OrderItemDTO> orderItemDTOS = Arrays.asList(orderItemDTO1, orderItemDTO2);
        orderRequestDTO.setItems(orderItemDTOS);
        orderRequestDTO.setUserId(user.getId());
        Order order = new Order();
        order.setOrderId("ORDER0001");
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.A);
        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder().
                orderId("ORDER0001").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(800.0).
                status(OrderStatus.A).build();
        when(orderService.createOrder(orderRequestDTO)).thenReturn(orderResponseDTO);
        ResponseEntity<OrderResponseDTO> result = orderController.createOrder(orderRequestDTO);
        assertNotNull(result);
    }

    @Test
    void test_GetOrders(){
        String status = "A";
        OrderResponseDTO orderResponseDTO1 = OrderResponseDTO.builder().
                orderId("ORDER0001").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(800.0).
                status(OrderStatus.A).build();
        OrderResponseDTO orderResponseDTO2 = OrderResponseDTO.builder().
                orderId("ORDER0002").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(500.0).
                status(OrderStatus.A).build();
        List<OrderResponseDTO> orderResponseDTOS = Arrays.asList(orderResponseDTO1, orderResponseDTO2);
        when(orderService.getOrders(anyString())).thenReturn(orderResponseDTOS);
        ResponseEntity<List<OrderResponseDTO>> result = orderController.getOrders(status);
        assertNotNull(result);
        assertEquals(orderResponseDTOS.size(), Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    void test_GetOrdersById(){
        Long userId = 1L;
        OrderResponseDTO orderResponseDTO1 = OrderResponseDTO.builder().
                orderId("ORDER0001").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(800.0).
                status(OrderStatus.A).build();
        OrderResponseDTO orderResponseDTO2 = OrderResponseDTO.builder().
                orderId("ORDER0002").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(500.0).
                status(OrderStatus.A).build();
        List<OrderResponseDTO> orderResponseDTOS = Arrays.asList(orderResponseDTO1, orderResponseDTO2);
        when(orderService.getOrdersById(anyLong())).thenReturn(orderResponseDTOS);
        ResponseEntity<List<OrderResponseDTO>> result = orderController.getOrdersById(userId);
        assertNotNull(result);
        assertEquals(orderResponseDTOS.size(), Objects.requireNonNull(result.getBody()).size());
    }
}
