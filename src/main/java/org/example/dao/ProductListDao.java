package org.example.dao;

import java.io.FileNotFoundException;

public interface ProductListDao {
    void loadLibrary() throws FileNotFoundException;
}
