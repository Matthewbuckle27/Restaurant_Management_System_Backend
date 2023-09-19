package com.maveric.restaurantmanagementsystem.service.impl;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import com.maveric.restaurantmanagementsystem.dto.ProductRequestDTO;
import com.maveric.restaurantmanagementsystem.dto.ProductResponseDTO;
import com.maveric.restaurantmanagementsystem.entity.Product;
import com.maveric.restaurantmanagementsystem.exception.ProductNotFoundException;
import com.maveric.restaurantmanagementsystem.exception.ServiceException;
import com.maveric.restaurantmanagementsystem.repository.ProductRepository;
import com.maveric.restaurantmanagementsystem.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        try {
            Product newProduct = modelMapper.map(productRequestDTO, Product.class);
            Product savedProduct = productRepository.save(newProduct);
            return modelMapper.map(savedProduct, ProductResponseDTO.class);
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.ADD_PRODUCT_FAILURE);
        }
    }

    @Override
    public List<ProductResponseDTO> getProducts() {
        try {
            List<Product> productList = productRepository.findAll();
            return productList.stream().map(product -> modelMapper.map(product, ProductResponseDTO.class)).toList();
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.GET_PRODUCTS_FAILURE);
        }
    }

    @Override
    public ProductResponseDTO changeProductAvailability(Long productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
            product.setProductAvailable(!product.isProductAvailable());
            Product updatedProduct = productRepository.save(product);
            return modelMapper.map(updatedProduct, ProductResponseDTO.class);
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.UPDATE_PRODUCT_FAILURE);
        }
    }

    @Override
    public ProductResponseDTO updateProductPrice(Long productId, Double price) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
            product.setPrice(price);
            Product updatedProduct = productRepository.save(product);
            return modelMapper.map(updatedProduct, ProductResponseDTO.class);
        } catch (DataAccessException | TransactionException e) {
            log.error(e.getMessage());
            throw new ServiceException(AppConstants.UPDATE_PRICE_FAILURE);
        }
    }
}
