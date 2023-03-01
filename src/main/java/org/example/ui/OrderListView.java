package org.example.ui;

public class OrderListView {

    private UserIO io = new UserIOConsoleImpl();

    public int printMenuAndGetSelection() {

        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readInt("\nPlease select from the available options. ", 1, 6);
    }

}
