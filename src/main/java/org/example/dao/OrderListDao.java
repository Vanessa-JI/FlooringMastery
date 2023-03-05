package org.example.dao;

import org.example.dto.Order;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// All methods defined in this interface involve direct manipulation of the Order DTO
// All processing of DTO information must be done by the DAO
public interface OrderListDao {

    /**
     * Returns an ArrayList of all Order objects in the inventory.
     *
     * @return ArrayList containing all Order objects in the inventory.
     */
    ArrayList<Order> getAllOrders(String orderDate);

    /**
     * Returns the order object associated with the given order date and number.
     * Returns null if no such student exists
     *
     * @param orderDate date of the order to retrieve
     * @param orderNumber order number of the order to retrieve
     * @return the order object associated with the given student id,
     * null if no such student exists
     */
    Order getAnOrder(String orderDate, Integer orderNumber);

    /**
     * Adds the given Order object to the inventory and associates it with the given
     * orderDate. If an invalid order date/customer name/state/product/area is input,
     * an error is thrown, otherwise, the order is added to the orderList.
     * @param order to be added to the roster
     */

    void addOrder(Order order);


    Order editOrder(String orderDate, String customerName);




//    /**
//     * Returns the student object associated with the given student id.
//     * Returns null if no such student exists
//     *
//     * @param studentId ID of the student to retrieve
//     * @return the Student object associated with the given student id,
//     * null if no such student exists
//     */
//    Student getStudent(String studentId);

    /**
     * Removes from the order list the order associated with the given order date and order number.
     * Returns the order object that is being removed or null if
     * there is no order associated with the given id
     *
     * @param orderDate date of order to be removed
     * @param orderNumber number of order to be removed
     * @return Order object that was removed or null if no order
     * was associated with the given order date and number
     */
    Order removeOrder(String orderDate, Integer orderNumber);

    Order exportAllData();

    void loadLibrary() throws FileNotFoundException;

    void writeLibrary();
}
