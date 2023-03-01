package org.example.controller;

import org.example.dao.OrderListDao;
import org.example.ui.OrderListView;
import org.example.ui.UserIO;
import org.example.ui.UserIOConsoleImpl;

import java.util.ArrayList;

public class OrderListController {
    private OrderListView view;
    private OrderListDao dao;
    private UserIO io = new UserIOConsoleImpl();

    // constructor instantiates both the DAO and the View
//    public OrderListController(OrderListDao dao, OrderListView view) {
//        this.dao = dao; // handles retrieval and storage of OrderList data
//        this.view = view; // needed for user interaction
//    }

    // run method kicks off execution of entire program
    public void run() {
        boolean running = true;
        int selection = 0;
        while (running) {

            int menuSelection = view.printMenuAndGetSelection();

            switch (menuSelection) {
                case 1:
                    io.print("DISPLAY ORDERS");
                    break;
                case 2:
                    io.print("ADD AN ORDER");
                    break;
                case 3:
                    io.print("EDIT AN ORDER");
                    break;
                case 4:
                    io.print("REMOVE AN ORDER");
                    break;
                case 5:
                    io.print("EXPORT ALL DATA");
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
    } // End of run method
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

//    // defining a method that handles editing of DVD object information in the library
//    public void editInformation() {
//        view.displayEditDVDBanner(); // display banner to announce editing of information
//        String title = view.getTitleToSearch(); // view retrieves the title of required DVD from user
//        DVD dvd = dao.getDVD(title); // DAO retrieves DVD object using title as a search key
//        dao.removeDVD(title); // unedited DVD object is removed from library by DAO
//        view.displayDVD(dvd);
//        DVD newDVD = view.editDVDInformation(dvd);
//        dao.addDVD(newDVD.getTitle(), newDVD); // new edited DVD is returned and added to the library
//    }
//
//    // defining a method that handles the removal of required DVD object from the library
//    public void removeDVD() {
//        view.displayRemoveDVDBanner(); // inform user that they selected to remove DVD
//        String title = view.getTitleToSearch(); // view retrieves title of required DVD
//        DVD dvd = dao.removeDVD(title); // DAO removes required DVD object from library
//        view.displayRemoveResults(dvd); // view informs user if the removal of that DVD was successful
//    }
//
//    // defining a method that handles displaying all information about a DVD to a user
//    private void viewDVD() {
//        view.displayGetDVDBanner();
//        String title = view.getTitleToSearch(); // view retrieves title of required DVD
//        DVD dvd = dao.getDVD(title); // DAO retrieves DVD object using title as a search key
//        view.displayDVD(dvd); // view reports all DVD object information to user
//    }
//
//    // defining a method that lists all DVDs currently in the DVD library
//    private void listDVDs() {
//        view.displayListAllBanner();
//        ArrayList<DVD> dvdList = dao.getAllDVDs(); // DAO retrieves all DVDs in the library and returns them as a list
//        view.listAll(dvdList); // view lists all DVDs in the library
//    }
//
//    private void createDVD() {
//        view.displayCreateDVDBanner();
//        DVD newDVD = view.getNewDVD(); // view retrieves all information user inputs that is needed to create a new DVD
//        dao.addDVD(newDVD.getTitle(), newDVD); // DAO creates a new DVD object and adds it to the library
//        view.displayCreateSuccessBanner();
//    }
//
//    // private method to get the final menu selection
//    private int getUserSelection() {
//        return view.getMenuSelection();
//    }
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


