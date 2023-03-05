//package org.example.dao.OrderListDaoFileImpl;

import org.example.dao.OrderListDao;
import org.example.dao.OrderListDaoFileImpl;
import org.example.dto.Order;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OrderListDaoFileImplTest {

        OrderListDao testDao;

        public OrderListDaoFileImplTest() {
        }

        @BeforeEach
        public void setUp() throws Exception {
                String orderFolder = "Orders";
                new FileWriter(orderFolder + "/testorders.txt");
                testDao = new OrderListDaoFileImpl(orderFolder);
        }

        @Test
        public void testAddGetAllOrders() throws Exception {
                // Create our first order
                Order firstOrder = new Order();
                firstOrder.setOrderDate("01/01/2025");
                firstOrder.setCustomerName("J.Doe,Inc01");
                firstOrder.setState("TX");
                firstOrder.setProductType("Wood");
                firstOrder.setArea(new BigDecimal("150"));

                // Create our second order
                Order secondOrder = new Order();
                secondOrder.setOrderDate("01/01/2025");
                secondOrder.setCustomerName("G00Gle10");
                secondOrder.setState("CA");
                secondOrder.setProductType("Laminate");
                secondOrder.setArea(new BigDecimal("2000"));

                // Add both our orders to the DAO
                testDao.addOrder(firstOrder);
                testDao.addOrder(secondOrder);

                // Retrieve the list of all orders within the DAO
                ArrayList<Order> allOrders = testDao.getAllOrders("01/01/2025");

                // First check the general contents of the list
                assertNotNull(allOrders, "The list of orders must not be null");
                assertEquals(2, allOrders.size(),"List of orders should have 2 orders.");

                // Then the specifics
                assertTrue(testDao.getAllOrders("01/01/2025").contains(firstOrder),
                        "The list of orders should include J.Doe,Inc01.");
                assertTrue(testDao.getAllOrders("01/01/2025").contains(secondOrder),
                        "The list of students should include G00Gle10.");

        }

        @Test
        public void testRemoveOrder() throws Exception {
                // Create two new orders
                Order firstOrder = new Order();
                firstOrder.setOrderDate("01/01/2025");
                firstOrder.setCustomerName("J.Doe,Inc01");
                firstOrder.setState("TX");
                firstOrder.setProductType("Wood");
                firstOrder.setArea(new BigDecimal("150"));

                Order secondOrder = new Order();
                secondOrder.setOrderDate("01/01/2025");
                secondOrder.setCustomerName("G00Gle10");
                secondOrder.setState("CA");
                secondOrder.setProductType("Laminate");
                secondOrder.setArea(new BigDecimal("2000"));

                // Add both to the DAO
                testDao.addOrder(firstOrder);
                testDao.addOrder(secondOrder);

                // remove the first order - J.Doe,Inc01
                Order removedOrder = testDao.removeOrder("01/01/2025", firstOrder.getOrderNumber());

                // Check that the correct object was removed.
                assertEquals(removedOrder, firstOrder, "The removed order should be J.Doe,Inc01.");

                // Get all the orders
                ArrayList<Order> allOrders = testDao.getAllOrders("01/01/2025");

                // First check the general contents of the list
                assertNotNull( allOrders, "All orders list should be not null.");
                assertEquals( 1, allOrders.size(), "All orders should only have 1 order.");

                // Then the specifics
                assertFalse( allOrders.contains(firstOrder), "All orders should NOT include J.Doe,Inc01.");
                assertTrue( allOrders.contains(secondOrder), "All orders should include G00Gle10.");

                // Remove the second order
                removedOrder = testDao.removeOrder("01/01/2025", secondOrder.getOrderNumber());
                // Check that the correct object was removed.
                assertEquals( removedOrder, secondOrder, "The removed order should be G00Gle10.");

                // retrieve all the orders again, and check the list.
                allOrders = testDao.getAllOrders("01/01/2025");

                // Check the contents of the list - it should be empty
                assertTrue( allOrders.isEmpty(), "The retrieved list of orders should be empty.");

                // Try to 'get' both orders by their old id - they should be null!
                Order retrievedStudent = testDao.getAnOrder("01/01/2025", firstOrder.getOrderNumber());
                assertNull(retrievedStudent, "J.Doe,Inc01 was removed, should be null.");

                retrievedStudent = testDao.getAnOrder("01/01/2025", secondOrder.getOrderNumber());
                assertNull(retrievedStudent, "G00Gle10 was removed, should be null.");

        }

}
