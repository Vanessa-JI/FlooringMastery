package org.example.ui;

import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.Tax;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderListView {

    private UserIO io = new UserIOConsoleImpl();

    public OrderListView(UserIO myIo) {
    }

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

    public Order getNewOrderInfo(ArrayList<Product> allProducts, ArrayList<Tax> allTaxes) throws ParseException {
        Order newOrder = new Order();
        String orderDate = io.readString("Please enter the order date in the form MM/DD/YYYY: ");
        orderDate = checkOrderDate(orderDate);
        String customerName = io.readString("\nPlease enter the customer name: ");
        customerName = checkCustomerName(customerName);
        String state = io.readString("\nPlease enter the state to ship to: ");
        state = checkState(state, allTaxes, newOrder);

        io.print("\nAvailable products: ");
        io.print("  - Carpet");
        io.print("      * Cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("carpet")).collect(Collectors.toList()).get(0).getCostPerSquareFoot());
        io.print("      * Labor cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("carpet")).collect(Collectors.toList()).get(0).getLaborCostPerSquareFoot());
        io.print("  - Laminate");
        io.print("      * Cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("laminate")).collect(Collectors.toList()).get(0).getCostPerSquareFoot());
        io.print("      * Labor cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("laminate")).collect(Collectors.toList()).get(0).getLaborCostPerSquareFoot());
        io.print("  - Tile");
        io.print("      * Cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("tile")).collect(Collectors.toList()).get(0).getCostPerSquareFoot());
        io.print("      * Labor cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("tile")).collect(Collectors.toList()).get(0).getLaborCostPerSquareFoot());
        io.print("  - Wood");
        io.print("      * Cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("wood")).collect(Collectors.toList()).get(0).getCostPerSquareFoot());
        io.print("      * Labor cost per square foot: £" + allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase("wood")).collect(Collectors.toList()).get(0).getLaborCostPerSquareFoot());

        String productType = io.readString("\nFrom the available products shown, which product would you like to purchase?");
        productType = checkProductType(productType, allProducts, newOrder);

        String area = io.readString("\nWhat area (in square feet) would you like to purchase (minimum size 100sq.ft)?");
        area = checkArea(area);

        newOrder.setOrderDate(orderDate);
        newOrder.setCustomerName(customerName);
        newOrder.setState(state.toUpperCase());
        newOrder.setProductType(productType);
        newOrder.setArea(new BigDecimal(area));
        Product currProduct = getProduct(productType, allProducts);

        newOrder.setCostPerSquareFoot(new BigDecimal(getProduct(productType, allProducts).getCostPerSquareFoot()));
        newOrder.setMaterialCost();
        newOrder.setLaborCostPerSquareFoot(new BigDecimal(getProduct(productType, allProducts).getLaborCostPerSquareFoot()));
        newOrder.setLaborCost();
        newOrder.setTaxRate(new BigDecimal(getTax(state, allTaxes).getTaxRate()));
        newOrder.setTax();
        newOrder.setTotal();

        return newOrder;
    }

    public String checkOrderDate(String orderDate) throws ParseException {
        boolean dateIsValid = false;
        while (!dateIsValid) {
//            try {
                Date futureDate = new SimpleDateFormat("MM/dd/yyyy").parse(orderDate);
                if (futureDate.before(new Date())) {
                    orderDate = io.readString("Please enter a valid order date. The order date must be in the future.");
                } else {
                    break;
                }
//            } catch (ParseException e) {
//                io.readString("Please enter a valid date in the form MM/DD/YYYY: ");
//            }
        }

        return orderDate;
    }

    public String checkCustomerName(String customerName) {
        boolean nameIsValid = false;
        while (!nameIsValid) {
            if (customerName.isBlank()) {
                customerName = io.readString("Please enter a valid name. The name must contain " +
                        "only alphanumeric characters, commas, and periods.");
                continue;
            }
            // defining conditions for a valid name
            for (int i = 0; i < customerName.length(); i++) {
                char c = customerName.charAt(i);
                if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(Character.isDigit(c)) && (c != '.') && (c != ',')) {
                    customerName = io.readString("Please enter a valid name. The name must contain " +
                            "only alphanumeric characters, commas, and periods.");
                }
            }
            nameIsValid = true;
        }
        return customerName;
    }

    public Product getProduct(String productType, ArrayList<Product> allProducts) {
        Product currProduct = new Product(productType);

        // using lambdas and streams instead of a for loop
        List<Product> relevantProd = allProducts.stream().filter((p) -> p.getProductType().equalsIgnoreCase(productType)).collect(Collectors.toList());
        return relevantProd.get(0);
    }

//        for (Product product : allProducts) {
//
//            if (product.getProductType().toLowerCase().equals(productType.toLowerCase())) {
//                return product;
//            }
//        }
//        return currProduct;
//    }

    public String checkProductType(String productType, ArrayList<Product> allProducts, Order newOrder) {
        ArrayList<String> productNames = new ArrayList<>();
        double costPerSquareFoot;
        double laborCostPerSquareFoot;
        for (Product product : allProducts) {
            productNames.add(product.getProductType().toLowerCase());
        } // COULD FIGURE OUT A BETTER PLACE TO PUT THIS CODE
        boolean productIsValid = false;
        while (!productIsValid) {
            if (productNames.contains(productType.toLowerCase())) {
                // find the product object with the associated productType
                Product currProduct = getProduct(productType, allProducts);
                costPerSquareFoot = currProduct.getCostPerSquareFoot();
                laborCostPerSquareFoot = currProduct.getLaborCostPerSquareFoot();
                newOrder.setCostPerSquareFoot(new BigDecimal(costPerSquareFoot));
                newOrder.setLaborCostPerSquareFoot(new BigDecimal(laborCostPerSquareFoot));
                break;
            } else {
                productType = io.readString("Please enter a product shown in the list above.");
            }
        }
        return productType.substring(0, 1).toUpperCase() + productType.substring(1);
    }

    public String checkArea(String area) {
        boolean areaIsValid = false;
        while (!areaIsValid) {
            if ((new BigDecimal(area).compareTo(new BigDecimal("100"))) >= 0) {
                break;
            }
            area = io.readString("Please enter a valid value for area. The area must be a positive number of " +
                    "minimum value 100sq.ft");
        }
        return area;
    }

    public Tax getTax(String state, ArrayList<Tax> allTaxes) {
        Tax currTax = new Tax(state);
        // using lambdas and streams instead of a for loop
        List<Tax> relevantTax = allTaxes.stream().filter((p) -> p.getState().equalsIgnoreCase(state) || p.getStateName().equalsIgnoreCase(state)).collect(Collectors.toList());
        return relevantTax.get(0);

//        for (Tax tax : allTaxes) {
//            if ( tax.getStateName().toLowerCase().equals(state.toLowerCase()) ||
//                    tax.getState().toLowerCase().equals(state.toLowerCase()) ){
//                return tax;
//            }
//        }
//        return currTax;
    }

    public String checkState(String state, ArrayList<Tax> allTaxes, Order newOrder) {

        // look through arrayList of Taxes
        ArrayList<String> taxNames = new ArrayList<>();
        for (Tax tax : allTaxes) {
            taxNames.add(tax.getState().toLowerCase());
            taxNames.add(tax.getState().toLowerCase());
        } // COULD FIGURE OUT A BETTER PLACE TO PUT THIS CODE
        double taxRate;
        boolean stateIsValid = false;
        while (!stateIsValid) {
                if (taxNames.contains(state.toLowerCase())) {
                    Tax tax = getTax(state, allTaxes);
                    taxRate = tax.getTaxRate();
                    newOrder.setTaxRate(new BigDecimal(taxRate));
                    return state;
                }
                state = io.readString("Please try another state.");
            }
        return state;
    }

    public void displayAddOrderBanner() {
        io.print("=== Create new order ===\n");
    }

    public void createOrderSuccessBanner() {
        io.print("\nSuccessfully recorded new order.\n");
        io.readString("Please hit enter to continue.");
    }

    public int getOrderNumber() {
        Integer orderNumber = io.readInt("Please enter the order number: ");
        return orderNumber;
    }

    public String getOrderDate() {
        String orderDate = io.readString("Please enter the order date in the form MM/DD/YYYY: ");
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
        io.print("=== Displaying all orders ===\n");
    }

    public void displayAllOrders(ArrayList<Order> allOrders) {
        for (Order currentOrder : allOrders) {
            displayAnOrder(currentOrder);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveOrderBanner() {
        io.print("=== Removing an order ===\n");
    }

    public void displayRemoveResults() {
        io.print("\nOrder successfully removed.\n");
        io.readString("Please hit enter to continue. ");
    }

    public void displayEditOrderBanner() {
        io.print("=== Editing order ===\n");
    }

    public void displayEditSuccessBanner(Order order) {
        io.print("\nEdit complete. The new order details are listed below: ");
        displayAnOrder(order);
        io.readString("Please hit enter to continue. ");
    }

    public ArrayList<Object> getOrderNumberAndDate() {
        String orderDate = io.readString("Please enter the order date in the form MM/DD/YYYY: ");
        int orderNumber = io.readInt("Please enter the order number: ");
        ArrayList<Object> orderDetails = new ArrayList<>();
        orderDetails.add(orderDate);
        orderDetails.add(orderNumber);
        return orderDetails;
    }

    public Order editOrderInformation(Order order, ArrayList<Product> allProducts, ArrayList<Tax> allTaxes) {
        String customerName = io.readString("The current customer name is '" + order.getCustomerName()
                + "'. \nPlease enter a new value or hit the ENTER key if you do not wish to change it: ");
        if (!customerName.isBlank()) {
            checkCustomerName(customerName);
        }

        String state = io.readString("\nThe current state is '" + order.getState()
                + "'. \nPlease enter a new value or hit the ENTER key if you do not wish to change it: ");
        if (!state.isBlank()) {
            checkState(state, allTaxes, order);
        }

        String productType = io.readString("\nThe current product type is '" + order.getProductType()
                + "'. \nPlease enter a new value or hit the ENTER key if you do not wish to change it: ");
        if (!productType.isBlank()) {
            checkState(state, allTaxes, order);
        }

        String area = io.readString("\nThe current area is '" + order.getArea()
                    + "'. \nPlease enter a new value or hit the ENTER key if you do not wish to change it: ");
        if (!area.isBlank()) {
            area = checkArea(area);
        }

        if (!customerName.equals("")) {
            order.setCustomerName(customerName);
        }
        if (!state.equals("")) {
            order.setState(state);
        }
        if (!productType.equals("")) {
            order.setProductType(productType);
        }
        if (!area.equals("")) {
            order.setArea(new BigDecimal(area));
        }

        return order;
    }

    public boolean confirmNewOrder(Order order) {

        String addOrder = io.readString("\nThe details of your new order are shown above. " +
                "Do you want to confirm and add the order? (Y/N)");
        if (addOrder.equalsIgnoreCase("y")) {
            return true;
        }
        return false;

    }

    public boolean confirmOrderRemoval(Order order) {
        String addOrder = io.readString("The details of the order to be removed are shown above. " +
                "Do you want to confirm and remove the order? (Y/N)");
        if (addOrder.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    public void confirmOrderRemovalFailure() {
        io.print("\nOrder was not removed.\n");
    }

    public void confirmOrderAdditionFailure() {
        io.print("\nOrder was not added.\n");
    }
}
