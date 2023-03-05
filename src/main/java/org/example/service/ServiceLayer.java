package org.example.service;

import org.example.dto.Order;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ServiceLayer {

    ArrayList<Order> getAllOrders(String orderDate);
    Order getAnOrder(String orderDate, Integer orderNumber);
//    void addOrder(Order order);

    void addOrder(Order order, HashMap<String, HashMap<Integer, Order>> allOrders);

    Order editOrder(String orderDate, String customerName);
    Order removeOrder(String orderDate, Integer orderNumber);
    Order exportAllData();
    String getDate(String fileName);
    Order unmarshallOrder(String orderAsText, String fileDate);
    void loadLibrary() throws FileNotFoundException;
    String marshallOrder(Order anOrder);
    void writeLibrary();

}
