package org.example.controller;

import org.example.dao.OrderListDao;
import org.example.dao.ProductListDao;
import org.example.dao.TaxListDao;
import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.Tax;
import org.example.ui.OrderListView;
import org.example.ui.UserIO;
import org.example.ui.UserIOConsoleImpl;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class OrderListController {
    private OrderListView view;
    private OrderListDao dao;
    private ProductListDao prodDao;
    private TaxListDao taxDao;
    private UserIO io = new UserIOConsoleImpl();

    // constructor instantiates both the DAO and the View
    public OrderListController(OrderListDao dao, ProductListDao prodDao, TaxListDao taxDao, OrderListView view) {
        this.dao = dao; // handles retrieval and storage of OrderList data
        this.view = view; // needed for user interaction
        this.prodDao = prodDao;
        this.taxDao = taxDao;
    }


    // run method kicks off execution of entire program
    public void startMainProgram() throws ParseException, FileNotFoundException {
        prodDao.loadLibrary();
        taxDao.loadLibrary();
        loadLibary();
//        writeLibrary();
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
        dao.loadLibrary();
    }

    public void writeLibrary() {
        dao.writeLibrary();
    }
    // defining method that controls the addition of an order to the orderList
    public void addOrder() throws ParseException, FileNotFoundException {
        // display to the user that we're adding an order (view)
        view.displayAddOrderBanner();
        // load most recent library
        loadLibary();
        ArrayList<Product> allProducts = prodDao.getAllProducts();
        ArrayList<Tax> allTaxes = taxDao.getAllTaxes();

        // get the order information from the user and return an Order object (view)
        Order newOrder = view.getNewOrderInfo(allProducts, allTaxes);
        // store the order information in the dao
        dao.addOrder(newOrder);
        // write to library
        writeLibrary();

        // display a successful addition or not
        view.createOrderSuccessBanner();

    }

    public void viewAllOrders() {
        view.displayAllOrdersBanner();
        String orderDate = view.getOrderDate();
        ArrayList<Order> allOrders = dao.getAllOrders(orderDate);
        view.displayAllOrders(allOrders);
    }

//    // defining a method that handles displaying all information about an order to a user
//    private void viewAnOrder() {
//        view.displayAnOrderBanner();
//        Integer orderNumber = view.getOrderNumber(); // view retrieves orderNumber of required order
//        Order order = dao.getAnOrder(orderNumber); // DAO retrieves Order object using orderNumber as a search key
//        view.displayAnOrder(order); // view reports all Order object information to user
//    }

    // defining a method that handles the removal of required Order object from the list
    public void removeOrder() throws FileNotFoundException {
        view.displayRemoveOrderBanner();
        loadLibary();
        String orderDate = view.getOrderDate();
        Integer orderNumber = view.getOrderNumber(); // view retrieves orderNumber of required order
        dao.removeOrder(orderDate, orderNumber); // DAO removes required Order object from library
        writeLibrary();
        view.displayRemoveResults(); // view informs user if the removal of that order was successful
    }

    public void editOrderInformation() throws FileNotFoundException {
        view.displayEditOrderBanner(); // display banner to announce editing of information
        loadLibary();
        ArrayList<Object> orderDetails = view.getOrderNumberAndDate(); // view retrieves the order number and date of required order from user
        String orderDate = (String) orderDetails.get(0);
        Integer orderNumber = (Integer) orderDetails.get(1);
        Order order = dao.getAnOrder(orderDate, orderNumber); // DAO retrieves order object using orderDetails as a search key
        ArrayList<Product> allProducts = prodDao.getAllProducts();
        ArrayList<Tax> allTaxes = taxDao.getAllTaxes();
//        dao.removeOrder(orderDate, orderNumber); // unedited Order object is removed from list by DAO
//        view.displayAnOrder(order);
        view.editOrderInformation(order, allProducts, allTaxes);
        writeLibrary();
//        dao.addOrder(newOrder.getOrderNumber(), newOrder); // new edited Order is returned and added to the library
        view.displayEditSuccessBanner(order);
    }
//
//    // load in a hashmap of hashmaps -- outer hashmap: k:v = orderDate:ordersHashMap, inner hashmap: k:v = orderNumber:orders
//    public void loadOrderLists() throws DVDLibraryDaoException {
//        view.displayLoadingBanner();
//        dao.loadLibrary();
//    }
//
//    public void loadProductList() throws DVDLibraryDaoException {
//        view.displayLoadingBanner();
//        dao.loadLibrary();
//    }
//
//    public void loadProductList() throws DVDLibraryDaoException {
//        view.displayLoadingBanner();
//        dao.loadLibrary();
//    }




} // End of class definition


    // run method kicks off execution of entire program
//    public void run() throws DVDLibraryDaoException {
//
//        // first, the library is loaded from a comma separated text file
//        loadLibrary();
//        boolean running = true;
//        int selection = 0;
//
//        // while loop runs infinitely until manually exited to allow user to continually select menu items
//        while (running) {
//            selection = getUserSelection();
//
//            // switch-case handles selection of different options by the user
//            switch (selection) {
//                case 1:
//                    displayOrders();
//                    break;
//                case 2:
//                    addOrder();
//                    break;
//                case 3:
//                    editOrder();
//                    break;
//                case 4:
//                    removeOrder();
//                    break;
//                case 5:
//                    exportData();
//                    break;
//                case 6:
//                    // when user selects to exit the program, write the new updated library to the text file and exit
//                    writeLibrary();
//                    System.out.println("\nGoodbye!");
//                    return;
//                default:
//                    // if user selects an invalid case, they are prompted to pick a valid menu options
//                    System.out.println("\nPlease pick a value from 1-6.");
//            } // End of switch-case
//        } // End of while loop
//    } // End of run function

//

//

//
//
//
//    // method to load the DVD library from a comma separated text file
//    public void loadLibrary() throws DVDLibraryDaoException {
//        view.displayLoadingBanner();
//        dao.loadLibrary();
//    }
//
//    // method to write the existing library into a comma separated text file
//    public void writeLibrary() throws DVDLibraryDaoException {
//        view.displayWritingBanner();
//        dao.writeLibrary();
//    }
//


