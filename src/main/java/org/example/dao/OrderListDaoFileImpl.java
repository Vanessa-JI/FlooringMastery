package org.example.dao;

import org.example.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

// store DTO information in the DAO
public class OrderListDaoFileImpl implements OrderListDao {

    // defining a private class attribute to hold all orders in a HashMap object
//    private HashMap<Integer, Order> orderList = new HashMap<>();

    // RIGHT NOW, WE'RE ASSUMING WE HAVE ONE ORDER LIST FOR ONE DATE -- WILL EXPAND FUNCTIONALITY TO BUILD MULTIPLE ORDER LISTS FOR MULTIPLE DATES
    // DO THIS BEFORE TACKLING FILE I/O
    private static HashMap<String, HashMap<Integer, Order>> allOrders = new HashMap<>();
    public static final String ORDER_FOLDER = "Orders";
    public static final String DELIMITER = ",";


    @Override
    public ArrayList<Order> getAllOrders(String orderDate) {
        HashMap orderForDate = allOrders.get(orderDate);
        return new ArrayList<>(orderForDate.values());
    }

    @Override
    public Order getAnOrder(String orderDate, Integer orderNumber) {
        return allOrders.get(orderDate).get(orderNumber);
    }

    @Override
    public void addOrder(Order order) {
        HashMap orderList = new HashMap<>();
        if (allOrders.containsKey(order.getOrderDate())) {
            orderList = allOrders.get(order.getOrderDate());
        }
        orderList.put(order.getOrderNumber(), order);
        allOrders.put(order.getOrderDate(), orderList);
    }

    @Override
    public Order editOrder(String orderDate, String customerName) {
        return null;
    }

    @Override
    public Order removeOrder(String orderDate, Integer orderNumber) {
        return allOrders.get(orderDate).remove(orderNumber);
    }

    @Override
    public Order exportAllData() {
        return null;
    }

    public String getDate(String fileName) {
        String[] fileNameArr = fileName.split("_");
        String orderDate = fileNameArr[1];
        String month = orderDate.substring(0, 2);
        String day = orderDate.substring(2, 4);
        String year = orderDate.substring(4);
        StringJoiner joiner = new StringJoiner("/");
        joiner.add(month).add(day).add(year);
        return joiner.toString();
    }


    // method to perform DVD object information unmarshalling
    private Order unmarshallOrder(String orderAsText, String fileDate) {
        // split the line read from text file into tokens (DVD object attributes)
        String[] orderTokens = orderAsText.split(DELIMITER);

        // generate a new DVD object based on the title retrieved from the text file
        String orderNumber = orderTokens[0];
        Order orderFromFile = new Order();

        // set all other instance attributes accordingly and return the DVD object created
        orderFromFile.setOrderDate(fileDate);
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost();
        orderFromFile.setLaborCost();
        orderFromFile.setTax();
        orderFromFile.setTotal();

        return orderFromFile;
    }

    // defining a method to read the library file from comma-separated text file into local memory
    // exception is thrown if there is an error reading the text file
    public void loadLibrary() throws FileNotFoundException {

        // define a new scanner object to read text file line by line
        Scanner scanner;

        // try-catch block handles the exception and prints out an error message if the text file cannot be read
//        try {

        // recursively loop through the orders directory
        File directory = new File(ORDER_FOLDER);
        File[] files = directory.listFiles();

        for (File file : files) {
            // create an array list for all orders of this order date
            HashMap<Integer, Order> ordersForThisDate = new HashMap<>();
            if (file.isFile()) {
                String fileName = file.getName().substring(0, 15); // removing the extension from fileName
                String orderDate = getDate(fileName);
                scanner = new Scanner(new BufferedReader(new FileReader(file)));
                scanner.nextLine();
                String currentLine;
                Order currentOrder;

                while (scanner.hasNextLine()) {
                    currentLine = scanner.nextLine();
                    currentOrder = unmarshallOrder(currentLine, orderDate);
                    ordersForThisDate.put(currentOrder.getOrderNumber(), currentOrder); // add this DVD object to the library of DVDs
                }
                scanner.close();
            }

            // when finished adding all orders from this file through this file, add this hashmap of orders to the allOrders hashmap
            allOrders.put(getDate(file.getName().substring(0, 15)), ordersForThisDate);
        }
    }

    // defining a function to perform data marshalling
    private String marshallOrder(Order anOrder) {
        // Turning each line of text in the input file into a DVD object
        String orderAsText = anOrder.getOrderNumber().toString() + DELIMITER;
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getMaterialCost() + DELIMITER;
        orderAsText += anOrder.getLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotal();

        return orderAsText; // return a String object that can be saved in a text file
    }


//    // Writes all DVDs in the libraries at the end of program execution to a LIBRARY_FILE.
//    // throws ClassRosterDaoException if an error occurs writing to the file
//    public void writeLibrary() {
//        PrintWriter out; // defining a PrintWriter object to save each line of text to
//
//        // try-catch block handles an exception if DVD data cannot be saved
//        try {
//            out = new PrintWriter(new FileWriter(ORDER_FOLDER));
//        } catch (IOException e) {
//            throw new DVDLibraryDaoException(
//                    "Could not save DVD data.", e);
//        }
//
//        // Write out the DVD objects to the library file
//        String dvdAsText;
//        ArrayList<DVD> dvdList = this.getAllDVDs();
//        for (DVD currentDVD : dvdList) {
//            dvdAsText = marshallOrder(currentDVD);
//            out.println(dvdAsText);
//            out.flush(); // force line to be written to file
//        }
//        out.close();
//    }

    public void writeLibrary() {
        try {
            for (Map.Entry<String, HashMap<Integer, Order>> set : allOrders.entrySet()) {
                String orderDate = set.getKey();
                String[] arr = orderDate.split("/");
                orderDate = arr[0] + arr[1] + arr[2];

                for (Map.Entry<Integer, Order> innerSet : set.getValue().entrySet()) {
                    Integer orderNumber = innerSet.getKey();
                    Order currOrder = innerSet.getValue();
                    // marshall order
                    String orderAsText = marshallOrder(currOrder);
                    String content = "";
                    // see if a file with the orderDate exists in the Orders folder
                    File file = new File("Orders/Order_" + orderDate + ".txt");
//                    File file  =  new File("Orders_Order");
                    if (!file.exists()) {
                        file.createNewFile();
                        content += "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n";
                    }
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(content + orderAsText + "\n");
                    bw.close();
                }
            }
        } catch(Exception ioe) {
                System.out.println("Exception occurred:");
                ioe.printStackTrace();
        }
    }
}
