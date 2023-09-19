package com.maveric.restaurantmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long productId;
    private String productName;
    private String description;
    private double price;
    private boolean isProductAvailable;

}
