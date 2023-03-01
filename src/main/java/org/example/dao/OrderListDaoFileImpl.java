package org.example.dao;

import org.example.dto.Order;

import java.util.ArrayList;
import java.util.HashMap;

// store DTO information in the DAO
public class OrderListDaoFileImpl implements OrderListDao{

    // defining a private class attribute to hold all orders in a HashMap object
    private HashMap<String, Order> orderList = new HashMap<>();


    @Override
    public ArrayList<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order addOrder(String orderDate, Order order) {
        Order newOrder = orderList.put(orderDate, order);
        return newOrder;
    }

    @Override
    public Order editOrder(String orderDate, String customerName) {
        return null;
    }

    @Override
    public Order removeOrder(String orderDate, String customerName) {
        return null;
    }

    @Override
    public Order exportAllData() {
        return null;
    }
}
