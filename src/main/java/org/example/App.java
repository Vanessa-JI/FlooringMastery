package org.example;

import org.example.controller.OrderListController;
import org.example.dao.OrderListDao;
import org.example.dao.OrderListDaoFileImpl;
import org.example.ui.OrderListView;

import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException {

        // instantiating all necessary objects
        OrderListView view = new OrderListView();
        OrderListDao dao = new OrderListDaoFileImpl();
        OrderListController controller = new OrderListController(dao, view);
        controller.startMainProgram();
    }
}
