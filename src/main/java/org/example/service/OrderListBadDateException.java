package org.example.service;

public class OrderListBadDateException extends Exception {

    public OrderListBadDateException(String message) {
        super(message);
    }

    public OrderListBadDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
