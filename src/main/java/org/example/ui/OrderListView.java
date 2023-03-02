package org.example.ui;

import org.example.dto.Order;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderListView {

    private UserIO io = new UserIOConsoleImpl();

    public int printMenuAndGetSelection() {

        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("\nPlease select from the available options. ", 1, 6);
    }

    public Order getNewOrderInfo() {
        String orderDate = io.readString("Please enter the order date in the form MM/DD/YYYY: ");
        String customerName = io.readString("Please enter the customer name: ");
        String state = io.readString("Please enter the state to ship to: ");

        // SHOW PRICES -- can the view communicate with the service layer to access information from a different DTO via a DAO

        String productType = io.readString("From the available products shown, which product would you like to purchase?");
        String area = io.readString("What area (in square feet) would you like to purchase (minimum size 100sq.ft)?");


        Order newOrder = new Order();
        newOrder.setCustomerName(customerName);
        newOrder.setState(state);
//        newOrder.setTaxRate();
        newOrder.setProductType(productType);
        newOrder.setArea(new BigDecimal(area));
//        newOrder.setCostPerSquareFoot();
//        newOrder.setLaborCostPerSquareFoot();
//        newOrder.setMaterialCost();
//        newOrder.setLaborCost();
//        newOrder.setTax();
//        newOrder.setTotal();

        return newOrder;
    }

    public void displayAddOrderBanner() {
        io.print("=== Create new order ===\n");
    }

    public void createOrderSuccessBanner() {
        io.print("Successfully recorded new order.\n");
    }

    public int getOrderNumber() {
        Integer orderNumber = io.readInt("Please enter the order number: ");
        return orderNumber;
    }

    public String getOrderDate() {
        String orderDate = io.readString("Please enter the order date: ");
        return orderDate;
    }

    public void displayAnOrderBanner() {
        io.print("=== Displaying order information ===");
    }
    public void displayAnOrder(Order currentOrder) {
        io.print("Details for order #" + currentOrder.getOrderNumber() + ":");
        io.print("      - Customer name: " + currentOrder.getCustomerName());
        io.print("      - State: " + currentOrder.getState());
        io.print("      - Product type: " + currentOrder.getProductType());
        io.print("      - Area: " + currentOrder.getArea() + "sq.ft \n");
    }

    public void displayAllOrdersBanner() {

        // ADD A CHECK IF THE ORDER IS EVEN IN THE DIRECTORY
        io.print("=== Displaying all orders ===");
    }

    public void displayAllOrders(ArrayList<Order> allOrders) {
        io.print("Orders for INSERT DATE: ");
        for (Order currentOrder : allOrders) {
            displayAnOrder(currentOrder);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveOrderBanner() {
        io.print("=== Removing an order ===");
    }

    public void displayRemoveResults() {
        io.print("Order successfully removed.\n");
        io.readString("Please hit enter to continue. ");
    }
}
