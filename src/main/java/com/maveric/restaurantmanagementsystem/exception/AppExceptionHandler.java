package com.maveric.restaurantmanagementsystem.exception;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.NULL_POINTER_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(ItemOutOfStockException.class)
    public ResponseEntity<Object> handleItemOutOfStockException(ItemOutOfStockException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.ITEM_OUT_OF_STOCK_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.PRODUCT_NOT_FOUND_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.USER_NOT_FOUND_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(NoOrderException.class)
    public ResponseEntity<Object> handleNoOrderException(NoOrderException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.NO_ORDER_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<Object> handleInvalidStatusException(InvalidStatusException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.INVALID_STATUS_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(EmptyItemException.class)
    public ResponseEntity<Object> handleItemNotAddedException(EmptyItemException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.ITEM_EMPTY_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (AppConstants.SERVICE_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }
}
