package com.maveric.restaurantmanagementsystem.service;

import com.maveric.restaurantmanagementsystem.dto.OrderItemDTO;
import com.maveric.restaurantmanagementsystem.dto.OrderRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.OrderResponseDTO;
import com.maveric.restaurantmanagementsystem.entity.Order;
import com.maveric.restaurantmanagementsystem.entity.OrderStatus;
import com.maveric.restaurantmanagementsystem.entity.Product;
import com.maveric.restaurantmanagementsystem.entity.User;
import com.maveric.restaurantmanagementsystem.exception.*;
import com.maveric.restaurantmanagementsystem.repository.ItemRepository;
import com.maveric.restaurantmanagementsystem.repository.OrderRepository;
import com.maveric.restaurantmanagementsystem.repository.ProductRepository;
import com.maveric.restaurantmanagementsystem.repository.UserRepository;
import com.maveric.restaurantmanagementsystem.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder(){
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        Product product1 = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        Product product2 = Product.builder().
                productId(2L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(true).build();
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(orderItemDTO1.getProductId())).thenReturn(Optional.of(product1));
        when(productRepository.findById(orderItemDTO2.getProductId())).thenReturn(Optional.of(product2));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(modelMapper.map(order, OrderResponseDTO.class)).thenReturn(orderResponseDTO);
        OrderResponseDTO result = orderService.createOrder(orderRequestDTO);
        assertNotNull(result);
        assertEquals(800.0, result.getTotalPrice());
        assertEquals(OrderStatus.A, result.getStatus());
    }

    @Test
    void testCreateOrder_UserNotFound(){
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> orderService.createOrder(orderRequestDTO));
    }

    @Test
    void testCreateOrder_ProductNotFound(){
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(orderItemDTO1.getProductId())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(orderRequestDTO));
    }

    @Test
    void testCreateOrder_ItemNotAvailable(){
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        Product product1 = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(false).build();
        Product product2 = Product.builder().
                productId(2L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(false).build();
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(orderItemDTO1.getProductId())).thenReturn(Optional.empty());
        when(productRepository.findById(orderItemDTO1.getProductId())).thenReturn(Optional.of(product1));
        when(productRepository.findById(orderItemDTO2.getProductId())).thenReturn(Optional.of(product2));
        assertThrows(ItemOutOfStockException.class, () -> orderService.createOrder(orderRequestDTO));
    }

    @Test
    void testCreateOrder_EmptyItem(){
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        Product product1 = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        Product product2 = Product.builder().
                productId(2L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(true).build();
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        OrderItemDTO orderItemDTO1 = new OrderItemDTO();
        orderItemDTO1.setProductId(1L);
        orderItemDTO1.setQuantity(0);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO();
        orderItemDTO2.setProductId(2L);
        orderItemDTO2.setQuantity(0);
        List<OrderItemDTO> orderItemDTOS = Arrays.asList(orderItemDTO1, orderItemDTO2);
        orderRequestDTO.setItems(orderItemDTOS);
        orderRequestDTO.setUserId(user.getId());
        Order order = new Order();
        order.setOrderId("ORDER0001");
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.A);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(orderItemDTO1.getProductId())).thenReturn(Optional.of(product1));
        when(productRepository.findById(orderItemDTO2.getProductId())).thenReturn(Optional.of(product2));
        assertThrows(EmptyItemException.class, () -> orderService.createOrder(orderRequestDTO));
    }

    @Test
    void testCreateOrder_ServiceException(){
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        Product product1 = Product.builder().
                productId(1L).
                productName("Chicken Biryani").
                description("500g Biryani").
                price(200.0).isProductAvailable(true).build();
        Product product2 = Product.builder().
                productId(2L).
                productName("Mutton Biryani").
                description("500g Biryani").
                price(300.0).isProductAvailable(true).build();
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
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(orderItemDTO1.getProductId())).thenReturn(Optional.of(product1));
        when(productRepository.findById(orderItemDTO2.getProductId())).thenReturn(Optional.of(product2));
        when(orderRepository.save(any(Order.class))).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> orderService.createOrder(orderRequestDTO));
    }

    @Test
    void testGetOrder(){
        String status = "A";
        Order order1 = new Order();
        order1.setOrderId("ORDER0001");
        order1.setOrderDate(LocalDateTime.now());
        order1.setTotalPrice(500.0);
        order1.setStatus(OrderStatus.A);
        Order order2 = new Order();
        order2.setOrderId("ORDER0002");
        order2.setOrderDate(LocalDateTime.now());
        order2.setTotalPrice(500.0);
        order2.setStatus(OrderStatus.A);
        OrderResponseDTO orderResponseDTO1 = OrderResponseDTO.builder().
                orderId("ORDER0001").
                orderDate(LocalDateTime.now()).
                totalPrice(500.0).
                status(OrderStatus.A).build();
        OrderResponseDTO orderResponseDTO2 = OrderResponseDTO.builder().
                orderId("ORDER0002").
                orderDate(LocalDateTime.now()).
                totalPrice(500.0).
                status(OrderStatus.A).build();
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        List<Order> orders = Arrays.asList(order1,order2);
        List<OrderResponseDTO> orderResponseDTOS = Arrays.asList(orderResponseDTO1,orderResponseDTO2);
        when(orderRepository.findByStatus(orderStatus)).thenReturn(orders);
        when(modelMapper.map(order1,OrderResponseDTO.class)).thenReturn(orderResponseDTO1);
        when(modelMapper.map(order2,OrderResponseDTO.class)).thenReturn(orderResponseDTO2);
        List<OrderResponseDTO> result = orderService.getOrders(status);
        assertNotNull(result);
        assertEquals(orderResponseDTOS, result);
    }

    @Test
    void testGetOrder_InvalidStatus() {
        String status = "M";
        assertThrows(InvalidStatusException.class, () -> orderService.getOrders(status));
    }

    @Test
    void testGetOrder_NoOrder() {
        String status = "A";
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByStatus(orderStatus)).thenReturn(orders);
        assertThrows(NoOrderException.class, () -> orderService.getOrders(status));
    }

    @Test
    void testGetOrder_ServiceException() {
        String status = "A";
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        when(orderRepository.findByStatus(orderStatus)).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> orderService.getOrders(status));
    }

    @Test
    void testGetOrderById(){
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        Order order1 = new Order();
        order1.setOrderId("ORDER0001");
        order1.setOrderDate(LocalDateTime.now());
        order1.setUser(user);
        order1.setTotalPrice(500.0);
        order1.setStatus(OrderStatus.A);
        Order order2 = new Order();
        order2.setOrderId("ORDER0002");
        order2.setOrderDate(LocalDateTime.now());
        order2.setUser(user);
        order2.setTotalPrice(500.0);
        order2.setStatus(OrderStatus.A);
        OrderResponseDTO orderResponseDTO1 = OrderResponseDTO.builder().
                orderId("ORDER0001").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(500.0).
                status(OrderStatus.A).build();
        OrderResponseDTO orderResponseDTO2 = OrderResponseDTO.builder().
                orderId("ORDER0002").
                orderDate(LocalDateTime.now()).
                userName("Matthew").
                totalPrice(500.0).
                status(OrderStatus.A).build();
        List<Order> orders = Arrays.asList(order1,order2);
        List<OrderResponseDTO> orderResponseDTOS = Arrays.asList(orderResponseDTO1,orderResponseDTO2);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRepository.findByUser(user)).thenReturn(orders);
        when(modelMapper.map(order1,OrderResponseDTO.class)).thenReturn(orderResponseDTO1);
        when(modelMapper.map(order2,OrderResponseDTO.class)).thenReturn(orderResponseDTO2);
        List<OrderResponseDTO> result = orderService.getOrdersById(1L);
        assertNotNull(result);
        assertEquals(orderResponseDTOS, result);
    }

    @Test
    void testGetOrderById_NoOrder() {
        User user = User.builder().
                id(1L).
                username("Matthew").
                password("matthew").
                email("matthew@gmail.com").
                role("user").build();
        List<Order> orders = new ArrayList<>();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRepository.findByUser(user)).thenReturn(orders);
        assertThrows(NoOrderException.class, () -> orderService.getOrdersById(1L));
    }

    @Test
    void testGetOrderById_ServiceException() {
        when(userRepository.findById(anyLong())).thenThrow(mock(DataAccessException.class));
        assertThrows(ServiceException.class, () -> orderService.getOrdersById(anyLong()));
    }
}
