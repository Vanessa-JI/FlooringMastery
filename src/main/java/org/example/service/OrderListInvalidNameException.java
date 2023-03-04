package org.example.service;

public class OrderListInvalidNameException extends Exception {
    public OrderListInvalidNameException(String message) {
            super(message);
        }
    public OrderListInvalidNameException(String message, Throwable cause) {
            super(message, cause);
    }
}
