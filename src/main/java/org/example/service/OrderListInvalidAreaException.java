package org.example.service;

public class OrderListInvalidAreaException extends Exception {
    public OrderListInvalidAreaException(String message) {
        super(message);
    }
    public OrderListInvalidAreaException(String message, Throwable cause) {
        super(message, cause);
    }
}
