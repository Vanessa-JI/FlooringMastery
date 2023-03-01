package org.example.dto;

// this class contains read-only attributes only -- the user cannot change any information in this file
public class Product {

    public Product() {
    }

    // read-only attributes
    private String productType;
    private double costPerSquareFoot;
    private double laborCostPerSquareFoot;

    // defining the getters (accessors) to allow outside of class access of private attributes
    public String getProductType() {
        return productType;
    }

    public double getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public double getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }
}
