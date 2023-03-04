package org.example.service;

public class OrderListInvalidProductSelectionException extends Exception {
    public OrderListInvalidProductSelectionException(String message) {

            super(message);
        }
    public OrderListInvalidProductSelectionException(String message, Throwable cause) {
            super(message, cause);

    }
}
