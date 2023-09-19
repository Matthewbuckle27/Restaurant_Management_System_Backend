package com.maveric.restaurantmanagementsystem.service.impl;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import com.maveric.restaurantmanagementsystem.dto.OrderItemDTO;
import com.maveric.restaurantmanagementsystem.dto.OrderRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.OrderResponseDTO;
import com.maveric.restaurantmanagementsystem.entity.*;
import com.maveric.restaurantmanagementsystem.exception.*;
import com.maveric.restaurantmanagementsystem.repository.ItemRepository;
import com.maveric.restaurantmanagementsystem.repository.OrderRepository;
import com.maveric.restaurantmanagementsystem.repository.ProductRepository;
import com.maveric.restaurantmanagementsystem.repository.UserRepository;
import com.maveric.restaurantmanagementsystem.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        try {
            User user = userRepository.findById(orderRequestDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
            List<OrderItemDTO> orderItemDTOS = orderRequestDTO.getItems();
            Order order = new Order();
            double totalPrice = 0.0;
            for (OrderItemDTO itemDTO : orderItemDTOS) {
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
                if (!product.isProductAvailable()) {
                    throw new ItemOutOfStockException(AppConstants.PRODUCT_OUT_OF_STOCK);
                }
                if(itemDTO.getQuantity()<1){
                    throw new EmptyItemException(AppConstants.EMPTY_ITEM);
                }
                Item item = new Item();
                item.setProduct(product);
                item.setQuantity(itemDTO.getQuantity());
                totalPrice += product.getPrice() * itemDTO.getQuantity();
                order.getItems().add(item);
            }
            order.setTotalPrice(totalPrice);
            order.setUser(user);

            Order savedOrder = orderRepository.save(order);

            for (Item item : order.getItems()) {
                item.setOrderId(savedOrder.getOrderId());
                itemRepository.save(item);
            }

            return modelMapper.map(savedOrder, OrderResponseDTO.class);
        }
        catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.CREATE_ORDER_FAILURE);
        }
    }

    @Override
    public List<OrderResponseDTO> getOrders(String status) {
        try {
            if (!(status.equalsIgnoreCase("A") || status.equalsIgnoreCase("C")
                    || status.equalsIgnoreCase("X"))) {
                throw new InvalidStatusException(AppConstants.WRONG_STATUS);
            }
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = orderRepository.findByStatus(orderStatus);
            if (orders.isEmpty()) {
                throw new NoOrderException(AppConstants.NO_ORDERS);
            }
            return orders.stream().map(order -> modelMapper.map(order, OrderResponseDTO.class)).toList();
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.GET_ORDER_FAILURE);
        }
    }

    @Override
    public List<OrderResponseDTO> getOrdersById(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
            List<Order> orders = orderRepository.findByUser(user);
            if (orders.isEmpty()) {
                throw new NoOrderException(AppConstants.NO_ORDERS);
            }
            return orders.stream().map(order -> modelMapper.map(order, OrderResponseDTO.class)).toList();
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.GET_ORDER_FAILURE);
        }
    }

}
