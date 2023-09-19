package com.maveric.restaurantmanagementsystem.exception;

public class EmptyItemException extends RuntimeException{
    public EmptyItemException(String message) {
        super(message);
    }
}
