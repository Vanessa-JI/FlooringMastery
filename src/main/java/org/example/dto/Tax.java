package org.example.dto;

// this class contains read-only attributes only -- the user cannot change any information in this file
public class Tax {

    public Tax() {
    }

    // read only attributes
    private String state;
    private String stateName;
    private double taxRate;


    // defining the getters (accessors) to allow outside of class access of private attributes
    public String getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
