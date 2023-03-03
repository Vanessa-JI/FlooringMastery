package org.example.dto;

// this class contains faux read-only attributes only -- the user cannot change any information in this file
public class Tax {

    public Tax(String state) {
    }

    // read only attributes
    private String state;
    private String stateName;
    private double taxRate;


    // defining the getters (accessors) and setters (mutators) to allow outside of class access of private attributes
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
