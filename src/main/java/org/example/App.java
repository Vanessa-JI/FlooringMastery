package org.example;

import org.example.controller.OrderListController;
import org.example.dao.*;
import org.example.ui.OrderListView;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException, FileNotFoundException {

        // instantiating all necessary objects
        OrderListView view = new OrderListView();
        OrderListDao dao = new OrderListDaoFileImpl();
        ProductListDao prodDao = new ProductListDaoFileImpl();
        TaxListDao taxDao = new TaxListDaoFileImpl(); //    WHY DO WE SAY NEW FILEIMPL BUT DECLARE AS A DAO OBJECT
        OrderListController controller = new OrderListController(dao, prodDao, taxDao, view);
        controller.startMainProgram();
    }
}
