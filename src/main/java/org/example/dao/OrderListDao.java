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

    Order getAnOrder(String orderDate, Integer orderNumber);

    /**
     * Adds the given Order object to the inventory and associates it with the given
     * orderDate. If there is already an order associated with the given
     * orderDate and customer name,
     * ,#####################EDIT THE DOCUMENTATION FROM HERE####################### it will return that student object, otherwise it will
     * return null.
     *
     * @param order to be added to the roster
     * @return the Student object previously associated with the given
     * student id if it exists, null otherwise
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
     * Removes from the roster the student associated with the given id.
     * Returns the student object that is being removed or null if
     * there is no student associated with the given id
     *
     * @param orderDate id of student to be removed
     * @return Student object that was removed or null if no student
     * was associated with the given student id
     */
    Order removeOrder(String orderDate, Integer orderNumber);

    Order exportAllData();

    void loadLibrary() throws FileNotFoundException;

    void writeLibrary();
}
