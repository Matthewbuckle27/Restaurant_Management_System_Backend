package com.maveric.restaurantmanagementsystem.entity;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = AppConstants.ORDER_TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AppConstants.ORDER_ID)
    @GenericGenerator(name = AppConstants.ORDER_ID, strategy = AppConstants.ORDER_ID_STRATEGY)
    @Column(updatable = false, nullable = false)
    private String orderId;

    private LocalDateTime orderDate;
    private double totalPrice;

    @Transient
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
        status = OrderStatus.A;
    }

}
