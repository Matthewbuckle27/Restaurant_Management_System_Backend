package com.maveric.restaurantmanagementsystem.dto;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = AppConstants.PRODUCT_NAME_NOTNULL)
    private String productName;

    @NotBlank(message = AppConstants.DESCRIPTION_NOTNULL)
    private String description;

    @Min(value = 1, message = AppConstants.PRICE_MIN)
    private double price;
}
