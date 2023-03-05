package org.example.service;

import org.example.dao.OrderListDao;
import org.example.dao.ProductListDao;
import org.example.dao.TaxListDao;
import org.example.dto.Order;
import org.example.dto.Product;
import org.example.dto.Tax;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ServiceLayerFileImpl implements ServiceLayer {

    OrderListDao dao;
    ProductListDao prodDao;
    TaxListDao taxDao;

    public ServiceLayerFileImpl(OrderListDao dao, ProductListDao prodDao, TaxListDao taxDao) {
        this.dao = dao;
        this.prodDao = prodDao;
        this.taxDao = taxDao;
    }

    @Override
    public ArrayList<Order> getAllOrders(String orderDate) throws ParseException, OrderListBadDateException {
        validateOrderDateInMemory(orderDate);
        return dao.getAllOrders(orderDate);
    }

    @Override
    public Order getAnOrder(String orderDate, Integer orderNumber) throws ParseException, OrderListBadDateException {
        validateOrderDateInMemory(orderDate);
        return dao.getAnOrder(orderDate, orderNumber);
    }

    @Override
    public void addOrder(Order order) throws ParseException, OrderListBadDateException {
        dao.addOrder(order);
    }

    @Override
    public Order removeOrder(String orderDate, Integer orderNumber) {
        return dao.removeOrder(orderDate, orderNumber);
    }

    @Override
    public Order exportAllData() {
        return dao.exportAllData();
    }

    @Override
    public String getDate(String fileName) {
        return null;
    }

    @Override
    public Order unmarshallOrder(String orderAsText, String fileDate) {
        return null;
    }

    @Override
    public void loadLibrary() throws FileNotFoundException {
        dao.loadLibrary();
    }

    @Override
    public String marshallOrder(Order anOrder) {
        return null;
    }

    @Override
    public void writeLibrary() {
        dao.writeLibrary();
    }

    @Override
    public void loadProductLibrary() throws FileNotFoundException {
        prodDao.loadLibrary();
    }

    @Override
    public void loadTaxLibrary() throws FileNotFoundException {
        taxDao.loadLibrary();
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return prodDao.getAllProducts();
    }

    @Override
    public ArrayList<Tax> getAllTaxes() {
        return taxDao.getAllTaxes();
    }

    public String validateOrderDateInMemory(String orderDate) throws ParseException, OrderListBadDateException {
        boolean dateIsValid = false;
        while (!dateIsValid) {
            Date futureDate = new SimpleDateFormat("MM/dd/yyyy").parse(orderDate);
            if (dao.getAllOrders(orderDate) == null) {
                throw new OrderListBadDateException("Error: no orders exist for the input date.");
            } else {
                break;
            }
        }

        return orderDate;
    }

    public String checkOrderDate(String orderDate) throws ParseException, OrderListBadDateException {
        boolean dateIsValid = false;
        while (!dateIsValid) {
            Date futureDate = new SimpleDateFormat("MM/dd/yyyy").parse(orderDate);
            if (futureDate.before(new Date())) {
                throw new OrderListBadDateException("Error: order date must be in the future.");
            } else {
                break;
            }
        }

        return orderDate;
    }

    public String checkCustomerName(String customerName) throws OrderListInvalidNameException {
        boolean nameIsValid = false;
        while (!nameIsValid) {
            if (customerName.isBlank()) {
                throw new OrderListInvalidNameException("Error: enter a valid name. The name must contain " +
                        "only alphanumeric characters, commas, and periods.");
//                customerName = io.readString("Please enter a valid name. The name must contain " +
//                        "only alphanumeric characters, commas, and periods.");
//                continue;
            }
            // defining conditions for a valid name
            for (int i = 0; i < customerName.length(); i++) {
                char c = customerName.charAt(i);
                if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(Character.isDigit(c)) && (c != '.') && (c != ',')) {
                    throw new OrderListInvalidNameException("Error: enter a valid name. The name must contain " +
                            "only alphanumeric characters, commas, and periods.");
//                    customerName = io.readString("Please enter a valid name. The name must contain only alphanumeric characters, commas, and periods.");
                }
            }
            nameIsValid = true;
        }
        return customerName;
    }

    public Product getProduct(String productType, ArrayList<Product> allProducts) {
        Product currProduct = new Product(productType);
        for (Product product : allProducts) {
            if (product.getProductType().equalsIgnoreCase(productType)) {
                return product;
            }
        }
        return currProduct;
    }

    public String checkProductType(String productType, ArrayList<Product> allProducts, Order newOrder) throws OrderListInvalidProductSelectionException {
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
                throw new OrderListInvalidProductSelectionException("Error: enter a product shown in the list above.");
//                productType = io.readString("Please enter a product shown in the list above.");
            }
        }
        return productType.substring(0, 1).toUpperCase() + productType.substring(1);
    }

    public String checkArea(String area) throws OrderListInvalidAreaException {
        boolean areaIsValid = false;
        while (!areaIsValid) {
            if ((new BigDecimal(area).compareTo(new BigDecimal("100"))) >= 0) {
                break;
            }
            throw new OrderListInvalidAreaException("Error: enter a valid value for area. The area must be " +
                    "a positive number of minimum value 100sq.ft");
//            area = io.readString("Please enter a valid value for area. The area must be a positive number of " +
//                    "minimum value 100sq.ft");
        }
        return area;
    }

    public Tax getTax(String state, ArrayList<Tax> allTaxes) {
        Tax currTax = new Tax(state);
        for (Tax tax : allTaxes) {
            if ( tax.getStateName().toLowerCase().equals(state.toLowerCase()) ||
                    tax.getState().toLowerCase().equals(state.toLowerCase()) ){
                return tax;
            }
        }
        return currTax;
    }

    public String checkState(String state, ArrayList<Tax> allTaxes, Order newOrder) throws OrderListInvalidStateException {

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
            throw new OrderListInvalidStateException("Error: please try another state");
//            state = io.readString("Please try another state.");
        }
        return state;
    }
}
