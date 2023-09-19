package com.maveric.restaurantmanagementsystem.dto;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = AppConstants.USERID_NOTNULL)
    private Long userId;

    @NotNull(message = AppConstants.ITEMS_NOTNULL)
    private List<OrderItemDTO> items;
}

