package org.example.controller;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.Tax;
import org.example.service.OrderListBadDateException;
import org.example.service.ServiceLayer;
import org.example.ui.OrderListView;
import org.example.ui.UserIO;
import org.example.ui.UserIOConsoleImpl;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class OrderListController {
    // defining the necessary members for the class
    private OrderListView view;
    private ServiceLayer service;
    private UserIO io = new UserIOConsoleImpl();

    // constructor instantiates both the DAO and the View
    public OrderListController(ServiceLayer service, OrderListView view) {
        this.service = service; //
        this.view = view; // needed for user interaction
    }


    // run method kicks off execution of entire program
    public void startMainProgram() throws ParseException, FileNotFoundException, OrderListBadDateException {
        loadLibary();
        boolean running = true;
        int selection = 0;
        while (running) {

            int menuSelection = view.printMenuAndGetSelection();

            switch (menuSelection) {
                case 1:
                    viewAllOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrderInformation();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    writeLibrary();
                    break;
                case 6:
                    io.print("Exiting program...");
                    running = false;
                    break;
                default:
                    io.print("Unknown command. Please select from the available options.");
            }

        }
        io.print("GOOD BYE");
    } // End of start method

    public void loadLibary() throws FileNotFoundException {
        service.loadProductLibrary();
        service.loadTaxLibrary();
        service.loadLibrary();
    }

    public void writeLibrary() {
        service.writeLibrary();
    }
    // defining method that controls the addition of an order to the orderList
    public void addOrder() throws ParseException, FileNotFoundException, OrderListBadDateException {
        // display to the user that we're adding an order (view)
        view.displayAddOrderBanner();
        // load most recent library
        loadLibary();
        ArrayList<Product> allProducts = service.getAllProducts();
        ArrayList<Tax> allTaxes = service.getAllTaxes();

        // get the order information from the user and return an Order object (view)
        Order newOrder = view.getNewOrderInfo(allProducts, allTaxes);
        view.displayAnOrder(newOrder);
        boolean confirmation = view.confirmNewOrder(newOrder);
        if (confirmation) {
            // store the order information in the dao
            service.addOrder(newOrder);
            // write to library
            writeLibrary();

            // display a successful addition or not
            view.createOrderSuccessBanner();
        } else {
            view.confirmOrderAdditionFailure();
        }
    }

    public void viewAllOrders() throws ParseException, OrderListBadDateException {
        view.displayAllOrdersBanner();
        String orderDate = view.getOrderDate();
        ArrayList<Order> allOrders = service.getAllOrders(orderDate);
        view.displayAllOrders(allOrders);
    }

    // defining a method that handles the removal of required Order object from the list
    public void removeOrder() throws FileNotFoundException, ParseException, OrderListBadDateException {
        view.displayRemoveOrderBanner();
        loadLibary();
        String orderDate = view.getOrderDate();
        Integer orderNumber = view.getOrderNumber(); // view retrieves orderNumber of required order
        boolean confirmation = view.confirmOrderRemoval(service.getAnOrder(orderDate, orderNumber));
        if (confirmation) {
            service.removeOrder(orderDate, orderNumber); // DAO removes required Order object from library
            writeLibrary();
            view.displayRemoveResults(); // view informs user if the removal of that order was successful
        } else {
            view.confirmOrderRemovalFailure();
        }
    }

    public void editOrderInformation() throws FileNotFoundException, ParseException, OrderListBadDateException {
        view.displayEditOrderBanner(); // display banner to announce editing of information
        loadLibary();
        ArrayList<Object> orderDetails = view.getOrderNumberAndDate(); // view retrieves the order number and date of required order from user
        String orderDate = (String) orderDetails.get(0);
        Integer orderNumber = (Integer) orderDetails.get(1);
        Order order = service.getAnOrder(orderDate, orderNumber); // DAO retrieves order object using orderDetails as a search key
        ArrayList<Product> allProducts = service.getAllProducts();
        ArrayList<Tax> allTaxes = service.getAllTaxes();
//        dao.removeOrder(orderDate, orderNumber); // unedited Order object is removed from list by DAO
//        view.displayAnOrder(order);
        view.editOrderInformation(order, allProducts, allTaxes);
        writeLibrary();
        view.displayEditSuccessBanner(order);
    }

} // End of class definition





