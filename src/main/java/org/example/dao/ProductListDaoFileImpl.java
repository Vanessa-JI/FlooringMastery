package org.example.dao;

import org.example.dto.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProductListDaoFileImpl implements ProductListDao {

    // defining two public constant class attributes and a private attribute
    public static final String PRODUCTS_FILE = "Products.txt";
    public static final String DELIMITER = ",";
    private HashMap<String, Product> products = new HashMap<>();

    // method to perform DVD object information unmarshalling
    private Product unmarshallProduct(String productAsText) {
        // split the line read from text file into tokens (DVD object attributes)
        String[] productTokens = productAsText.split(DELIMITER);

        // generate a new DVD object based on the title retrieved from the text file
        String productName = productTokens[0];
        Product productFromFile = new Product(productName);

        // set all other instance attributes accordingly and return the DVD object created
        productFromFile.setProductType(productTokens[0]);
        productFromFile.setCostPerSquareFoot(Double.parseDouble(productTokens[1]));
        productFromFile.setLaborCostPerSquareFoot(Double.parseDouble(productTokens[2]));
        return productFromFile;
    }

    // defining a method to read the library file from comma-separated text file into local memory
    // exception is thrown if there is an error reading the text file
    public void loadLibrary() throws FileNotFoundException {

        // define a new scanner object to read text file line by line
        Scanner scanner;

        // try-catch block handles the exception and prints out an error message if the text file cannot be read
//        try {
        scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
//        } catch (FileNotFoundException e) {
//            throw new DVDLibraryDaoException("Could not load DVD Library file.", e);
//        }
        scanner.nextLine();
        String currentLine;
        Product currentProduct;

        // read the text file line by line until the end and unmarshall each DVD object from the information in
        // the current line from the text file
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct); // add this DVD object to the library of DVDs
        }
        scanner.close();
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return new ArrayList<Product>(products.values());
    }
}
