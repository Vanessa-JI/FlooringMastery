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


    /**
     * Start method kicks off execution of entire program
      */
    public void startMainProgram() throws ParseException, FileNotFoundException, OrderListBadDateException {
        loadLibary();
        boolean running = true;
        while (running) { // menu options loop indefinitely until the user exits
            int menuSelection = view.printMenuAndGetSelection(); // view handles user input in main menu
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
        io.print("Goodbye!");
    } // End of start method

    /**
     * loadLibrary method loads the product library, tax library, and all orders from orders folder.
     * @throws FileNotFoundException if any text files cannot be found and read
     */
    public void loadLibary() throws FileNotFoundException {
        service.loadProductLibrary();
        service.loadTaxLibrary();
        service.loadLibrary();
    }

    /**
     * writeLibrary method writes entire order directory in the program to text files organised by order date
     */
    public void writeLibrary() {
        service.writeLibrary();
    }

    /**
     * addOrder method adds a newly input order to the order list stored in memory
     * @throws ParseException if the order date input by the user is not input in the correct format
     * @throws OrderListBadDateException if the input date from the user is not in the future
     */
    public void addOrder() throws ParseException, OrderListBadDateException {
        view.displayAddOrderBanner();
        ArrayList<Product> allProducts = service.getAllProducts(); // get the list of available products and tax states
        ArrayList<Tax> allTaxes = service.getAllTaxes();
        Order newOrder = view.getNewOrderInfo(allProducts, allTaxes); // get the order information from the user and return an Order object (view)
        view.displayAnOrder(newOrder);
        boolean confirmation = view.confirmNewOrder(newOrder); // returns true if the user confirms to add a new order
        if (confirmation) {
            service.addOrder(newOrder); // store the order information in the dao
            writeLibrary();
            view.createOrderSuccessBanner(); // display success if user chose to add order
        } else {
            view.confirmOrderAdditionFailure(); // display failure if user chose not to add order
        }
    }

    /**
     * viewAllOrders method allows the user to view all the orders that have been scheduled for a given day.
     * @throws ParseException if the order date input by the user is not input in the correct format
     * @throws OrderListBadDateException if the input date from the user is not in the future
     */
    public void viewAllOrders() throws ParseException, OrderListBadDateException {
        view.displayAllOrdersBanner();
        String orderDate = view.getOrderDate();
        ArrayList<Order> allOrders = service.getAllOrders(orderDate);
        view.displayAllOrders(allOrders);
    }

    /**
     * removeOrder method removes a required order object from the order list stored in local memory then writes
     * a new updated list to the file
     * @throws ParseException
     * @throws OrderListBadDateException
     */
    public void removeOrder() throws ParseException, OrderListBadDateException {
        view.displayRemoveOrderBanner();
        String orderDate = view.getOrderDate();
        Integer orderNumber = view.getOrderNumber(); // view retrieves orderNumber of required order
        boolean confirmation = view.confirmOrderRemoval(service.getAnOrder(orderDate, orderNumber));
        if (confirmation) { // if user selects to remove a given order, it is removed
            service.removeOrder(orderDate, orderNumber); // DAO removes required Order object from library
            writeLibrary();
            view.displayRemoveResults(); // view informs user if the removal of that order was successful
        } else {
            view.confirmOrderRemovalFailure();
        }
    }

    /**
     * editOrderInformation allows the user to edit the information of an existing order as required by the user
     * @throws ParseException
     * @throws OrderListBadDateException
     */
    public void editOrderInformation() throws ParseException, OrderListBadDateException {
        view.displayEditOrderBanner(); // display banner to announce editing of information
        ArrayList<Object> orderDetails = view.getOrderNumberAndDate(); // view retrieves the order number and date of required order from user
        String orderDate = (String) orderDetails.get(0);
        Integer orderNumber = (Integer) orderDetails.get(1);
        Order order = service.getAnOrder(orderDate, orderNumber); // DAO retrieves order object using orderDetails as a search key
        ArrayList<Product> allProducts = service.getAllProducts();
        ArrayList<Tax> allTaxes = service.getAllTaxes();
        view.editOrderInformation(order, allProducts, allTaxes);
        writeLibrary(); // save updated order list to text file
        view.displayEditSuccessBanner(order);
    }

} // End of class definition





