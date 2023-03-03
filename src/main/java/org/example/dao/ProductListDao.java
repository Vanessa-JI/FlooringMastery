package org.example.dao;

import org.example.dto.Product;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ProductListDao {
    void loadLibrary() throws FileNotFoundException;

    ArrayList<Product> getAllProducts();

}
