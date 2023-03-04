package org.example.service;

public class OrderListInvalidStateException extends Exception {
    public OrderListInvalidStateException(String message) {
            super(message);
        }
    public OrderListInvalidStateException(String message, Throwable cause) {
            super(message, cause);
        }

}
