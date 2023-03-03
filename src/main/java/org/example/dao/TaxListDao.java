package org.example.dao;

import java.io.FileNotFoundException;

public interface TaxListDao {
    void loadLibrary() throws FileNotFoundException;
}
