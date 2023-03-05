package org.example.dto;

import java.math.BigDecimal;
import java.util.Objects;

// DTO class -- contains only getters and setters
// Used to move data from one tier to another in applications
public class Order {
    // defining a single class attribute that increments by 1 every time an order is recorded
    private static int orders;

    // defining all instance attributes associated with each order
    private String orderDate;
    private Integer orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    // this constructor states that every time a new order is made, the number of orders increases by 1 and therefore
    // each order will be given a unique order number identifier

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Order() {
        this.orderNumber = orders += 1;
    }

    // defining all attribute getters and setters
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost() {
        BigDecimal val = this.area.multiply(this.costPerSquareFoot);
        this.materialCost = val;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost() {
        this.laborCost = this.area.multiply(this.laborCostPerSquareFoot);
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax() {
        this.tax = (this.materialCost.add(this.laborCost)).multiply(this.taxRate.divide(new BigDecimal("100")));
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = this.materialCost.add(this.laborCost).add(this.tax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getOrderDate(), order.getOrderDate()) && Objects.equals(getOrderNumber(), order.getOrderNumber()) && Objects.equals(getCustomerName(), order.getCustomerName()) && Objects.equals(getState(), order.getState()) && Objects.equals(getTaxRate(), order.getTaxRate()) && Objects.equals(getProductType(), order.getProductType()) && Objects.equals(getArea(), order.getArea()) && Objects.equals(getCostPerSquareFoot(), order.getCostPerSquareFoot()) && Objects.equals(getLaborCostPerSquareFoot(), order.getLaborCostPerSquareFoot()) && Objects.equals(getMaterialCost(), order.getMaterialCost()) && Objects.equals(getLaborCost(), order.getLaborCost()) && Objects.equals(getTax(), order.getTax()) && Objects.equals(getTotal(), order.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderDate(), getOrderNumber(), getCustomerName(), getState(), getTaxRate(), getProductType(), getArea(), getCostPerSquareFoot(), getLaborCostPerSquareFoot(), getMaterialCost(), getLaborCost(), getTax(), getTotal());
    }

    // overriding toString method to make visualisation of object parameters easier
    @Override
    public String toString() {
        return "Order{" +
                "orderDate='" + orderDate + '\'' +
                ", orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", materialCost=" + materialCost +
                ", laborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }
}
