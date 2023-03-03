package org.example;

import org.example.controller.OrderListController;
import org.example.dao.OrderListDao;
import org.example.dao.OrderListDaoFileImpl;
import org.example.dao.ProductListDao;
import org.example.dao.ProductListDaoFileImpl;
import org.example.ui.OrderListView;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException, FileNotFoundException {

        // instantiating all necessary objects
        OrderListView view = new OrderListView();
        OrderListDao dao = new OrderListDaoFileImpl();
        ProductListDao prodDao = new ProductListDaoFileImpl();
        OrderListController controller = new OrderListController(dao, prodDao, view);
        controller.startMainProgram();
    }
}
