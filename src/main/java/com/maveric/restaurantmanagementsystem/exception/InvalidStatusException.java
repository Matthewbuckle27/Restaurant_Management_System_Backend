package com.maveric.restaurantmanagementsystem.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {

        super(message);
    }
}