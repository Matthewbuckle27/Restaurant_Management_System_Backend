package com.maveric.restaurantmanagementsystem.repository;

import com.maveric.restaurantmanagementsystem.entity.Order;
import com.maveric.restaurantmanagementsystem.entity.OrderStatus;
import com.maveric.restaurantmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus orderStatus);
    List<Order> findByUser(User user);
}
