package org.example.dao;

import org.example.dto.Order;

import java.util.ArrayList;
import java.util.HashMap;

// store DTO information in the DAO
public class OrderListDaoFileImpl implements OrderListDao {

    // defining a private class attribute to hold all orders in a HashMap object
    private HashMap<Integer, Order> orderList = new HashMap<>();

    // RIGHT NOW, WE'RE ASSUMING WE HAVE ONE ORDER LIST FOR ONE DATE -- WILL EXPAND FUNCTIONALITY TO BUILD MULTIPLE ORDER LISTS FOR MULTIPLE DATES
    // DO THIS BEFORE TACKLING FILE I/O
     private HashMap<String, HashMap<Integer, Order>> allOrders = new HashMap<>();


    @Override
    // NEED TO UPDATE THIS SUCH THAT WE SEARCH FOR THE ORDER DATE TO FIND THE ORDER IN THE HASHMAP RELATED TO THIS DATE
    public ArrayList<Order> getAllOrders() {
        return new ArrayList<>(orderList.values());
    }

    @Override
    public Order getAnOrder(Integer orderNumber) {
        return orderList.get(orderNumber);
    }

    @Override
    public Order addOrder(Integer orderNumber, Order order) {
        Order newOrder = orderList.put(orderNumber, order);
        return newOrder;
    }

    @Override
    public Order editOrder(String orderDate, String customerName) {
        return null;
    }

    @Override
    public Order removeOrder(String orderDate, Integer orderNumber) {
        return orderList.remove(orderNumber);
    }

    @Override
    public Order exportAllData() {
        return null;
    }
}
