package com.maveric.restaurantmanagementsystem.service;

import com.maveric.restaurantmanagementsystem.dto.ProductRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> getProducts();
    ProductResponseDTO changeProductAvailability(Long productId);
    ProductResponseDTO updateProductPrice(Long productId, Double price);

}
