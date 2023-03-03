package org.example.dao;

import org.example.dto.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// store DTO information in the DAO
public class OrderListDaoFileImpl implements OrderListDao {

    // defining a private class attribute to hold all orders in a HashMap object
//    private HashMap<Integer, Order> orderList = new HashMap<>();

    // RIGHT NOW, WE'RE ASSUMING WE HAVE ONE ORDER LIST FOR ONE DATE -- WILL EXPAND FUNCTIONALITY TO BUILD MULTIPLE ORDER LISTS FOR MULTIPLE DATES
    // DO THIS BEFORE TACKLING FILE I/O
     private HashMap<String, HashMap<Integer, Order>> allOrders = new HashMap<>();


    @Override
    public ArrayList<Order> getAllOrders(String orderDate) {
        HashMap orderForDate = allOrders.get(orderDate);
        return new ArrayList<>(orderForDate.values());
    }

    @Override
    public Order getAnOrder(String orderDate, Integer orderNumber) {
        return allOrders.get(orderDate).get(orderNumber);
    }

    @Override
    public void addOrder(Order order) {
        HashMap orderList = new HashMap<>();
        if (allOrders.containsKey(order.getOrderDate())) {
            orderList = allOrders.get(order.getOrderDate());
        }
        orderList.put(order.getOrderNumber(), order);
        allOrders.put(order.getOrderDate(), orderList);
        // NEED TO SAVE TO THE FILE AFTER AN ORDER IS ADDED
    }

    @Override
    public Order editOrder(String orderDate, String customerName) {
        return null;
    }

    @Override
    public Order removeOrder(String orderDate, Integer orderNumber) {
        return allOrders.get(orderDate).remove(orderNumber);
    }

    @Override
    public Order exportAllData() {
        return null;
    }
}
