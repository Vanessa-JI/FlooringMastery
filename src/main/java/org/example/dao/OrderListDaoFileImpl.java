package org.example.dao;

import org.example.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

// store DTO information in the DAO
public class OrderListDaoFileImpl implements OrderListDao {

    // defining a private class attribute to hold all orders in a HashMap object
    private static HashMap<String, HashMap<Integer, Order>> allOrders = new HashMap<>();
    public final String ORDER_FOLDER;
    public static final String DELIMITER = ",";
    public OrderListDaoFileImpl() {
        ORDER_FOLDER = "Orders";
    }

    // second constructor allows user to select a different folder for orders
    public OrderListDaoFileImpl(String orderFolder) {
        ORDER_FOLDER = orderFolder;
    }

    @Override
    public ArrayList<Order> getAllOrders(String orderDate) {
        HashMap orderForDate = allOrders.get(orderDate); // get a hashmap of all orders for the given day
        if (orderForDate != null) {
            return new ArrayList<>(orderForDate.values());
        } else {
            return null; // return null if no orders exist for this day
        }
    }

    @Override
    public Order getAnOrder(String orderDate, Integer orderNumber) {
            return allOrders.get(orderDate).get(orderNumber); // return an order given the order date and number
    }

    @Override
    public void addOrder(Order order) {
        HashMap orderList = new HashMap<>();
        if (allOrders.containsKey(order.getOrderDate())) { // add order to pre-existing hashmap if available
            orderList = allOrders.get(order.getOrderDate());
        }
        orderList.put(order.getOrderNumber(), order); // otherwise add order to a new empty hashmap
        allOrders.put(order.getOrderDate(), orderList);
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
        String[] fileNameArr = fileName.split("_"); // get the date from the text file
        String orderDate = fileNameArr[1];
        String month = orderDate.substring(0, 2); // split the order date into day, month, year
        String day = orderDate.substring(2, 4);
        String year = orderDate.substring(4);
        StringJoiner joiner = new StringJoiner("/"); // join components of date together with relevant delimiter
        joiner.add(month).add(day).add(year);
        return joiner.toString(); // return joined string of complete date
    }

    private Order unmarshallOrder(String orderAsText, String fileDate) {
        // split the line read from text file into tokens (DVD object attributes)
        String[] orderTokens = orderAsText.split(DELIMITER);

        // generate a new DVD object based on the title retrieved from the text file
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

    public void loadLibrary() throws FileNotFoundException {
        // define a new scanner object to read text file line by line
        Scanner scanner;

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
                    ordersForThisDate.put(currentOrder.getOrderNumber(), currentOrder); // keep adding orders from the same date to a hashmap
                }
                scanner.close();
            }

            // when finished adding all orders from this file through this file, add this hashmap of orders to the allOrders hashmap
            allOrders.put(getDate(file.getName().substring(0, 15)), ordersForThisDate);
        }
    }

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

    public void writeLibrary() {
        try {
            for (Map.Entry<String, HashMap<Integer, Order>> set : allOrders.entrySet()) {
                String orderDate = set.getKey();
                String[] arr = orderDate.split("/");
                orderDate = arr[0] + arr[1] + arr[2];

                for (Map.Entry<Integer, Order> innerSet : set.getValue().entrySet()) { // get each hashmap in the hashmap of hashmaps
                    Order currOrder = innerSet.getValue();
                    // marshall order
                    String orderAsText = marshallOrder(currOrder);
                    String content = "";
                    // see if a file with the orderDate exists in the Orders folder
                    File file = new File("Orders/Orders_" + orderDate + ".txt");
                    if (!file.exists()) {
                        file.createNewFile();
                        content += "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n"; // write the relevant information to the text file
                    }
                    FileWriter fw = new FileWriter(file, true); // to ensure no overwriting of the text file, append is set to true
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
