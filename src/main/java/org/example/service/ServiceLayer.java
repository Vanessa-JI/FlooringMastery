package org.example.service;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.Tax;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ServiceLayer {

    ArrayList<Order> getAllOrders(String orderDate) throws ParseException, OrderListBadDateException;
    Order getAnOrder(String orderDate, Integer orderNumber) throws ParseException, OrderListBadDateException;
//    void addOrder(Order order);

    void addOrder(Order order) throws ParseException, OrderListBadDateException;

//    Order editOrder(String orderDate, String customerName);
    Order removeOrder(String orderDate, Integer orderNumber);
    Order exportAllData();
    String getDate(String fileName);
    Order unmarshallOrder(String orderAsText, String fileDate);
    void loadLibrary() throws FileNotFoundException;
    String marshallOrder(Order anOrder);
    void writeLibrary();

    void loadProductLibrary() throws FileNotFoundException;
    void loadTaxLibrary() throws FileNotFoundException;

    ArrayList<Product> getAllProducts();

    ArrayList<Tax> getAllTaxes();
}
