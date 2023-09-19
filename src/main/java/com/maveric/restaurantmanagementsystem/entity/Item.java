package com.maveric.restaurantmanagementsystem.entity;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = AppConstants.ITEM_TABLE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @ManyToOne
    @JoinColumn(name = AppConstants.PRODUCT_ID)
    private Product product;
    private String orderId;
    private int quantity;
}