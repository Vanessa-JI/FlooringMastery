package org.example;

import org.example.controller.OrderListController;
import org.example.dao.OrderListDao;
import org.example.ui.OrderListView;

public class App {
    public static void main(String[] args) {

        // instantiating all necessary objects
//        OrderListView view = new OrderListView();
//        OrderListDao dao = new OrderListDao();
        OrderListController controller = new OrderListController();
        controller.run();
    }
}