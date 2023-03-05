package org.example;

import org.example.controller.OrderListController;
import org.example.dao.*;
import org.example.service.OrderListBadDateException;
import org.example.ui.OrderListView;
import org.example.ui.UserIO;
import org.example.ui.UserIOConsoleImpl;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class App {
    public static void main(String[] args) throws ParseException, FileNotFoundException, OrderListBadDateException {

        // instantiating all necessary objects
//        UserIO myIo = new UserIOConsoleImpl();
//        OrderListView view = new OrderListView(myIo);
//        OrderListDao dao = new OrderListDaoFileImpl();
//        ProductListDao prodDao = new ProductListDaoFileImpl();
//        TaxListDao taxDao = new TaxListDaoFileImpl(); //    WHY DO WE SAY NEW FILEIMPL BUT DECLARE AS A DAO OBJECT
//        OrderListController controller = new OrderListController(dao, prodDao, taxDao, view);
//        controller.startMainProgram();


        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderListController controller = ctx.getBean("controller", OrderListController.class);
        controller.startMainProgram();
    }
}
