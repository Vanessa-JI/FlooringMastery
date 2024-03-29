package org.example.dao;

import org.example.dto.Product;
import org.example.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaxListDaoFileImpl implements TaxListDao {
    // defining two public constant class attributes and a private attribute
    public static final String TAXES_FILE = "Taxes.txt";
    public static final String DELIMITER = ",";
    private HashMap<String, Tax> taxes = new HashMap<>();

    // method to perform tax object information unmarshalling
    private Tax unmarshallTaxes (String taxAsText) {
        // split the line read from text file into tokens (tax object attributes)
        String[] taxTokens = taxAsText.split(DELIMITER);

        // generate a new tax object based on the state retrieved from the text file
        String taxName = taxTokens[0];
        Tax taxFromFile = new Tax(taxName);

        // set all other instance attributes accordingly and return the tax object created
        taxFromFile.setState(taxTokens[0]);
        taxFromFile.setStateName(taxTokens[1]);
        taxFromFile.setTaxRate(Double.parseDouble(taxTokens[2]));

        return taxFromFile;
    }

    // defining a method to read the library file from comma-separated text file into local memory
    // exception is thrown if there is an error reading the text file
    public void loadLibrary() throws FileNotFoundException {

        // define a new scanner object to read text file line by line
        Scanner scanner;
        scanner = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        scanner.nextLine();
        String currentLine;
        Tax currentTax;

        // read the text file line by line until the end and unmarshall each tax object from the information in
        // the current line from the text file
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTaxes(currentLine);
            taxes.put(currentTax.getState(), currentTax); // add this tax object to the library of taxes
        }
        scanner.close();
    }

    @Override
    public ArrayList<Tax> getAllTaxes() {
        return new ArrayList<Tax>(taxes.values());
    }
}
