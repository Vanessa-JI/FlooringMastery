package org.example.dto;

// this class contains faux read-only attributes only -- the user cannot change any information in this file, but these
// need to be set when the file is read in
public class Product {

    public Product(String productType) {
    }

    private String productType;
    private double costPerSquareFoot;
    private double laborCostPerSquareFoot;

    // defining the getters (accessors) and setters (mutators) to allow outside of class access of private attributes
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(double costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public double getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(double laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
}
