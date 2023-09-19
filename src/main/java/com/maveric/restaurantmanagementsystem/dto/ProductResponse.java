package com.maveric.restaurantmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
@NoArgsConstructor
public class ProductResponse {

    private String message;
    private HttpStatus httpStatus;
    private ProductResponseDTO productResponseDTO;

}
