package org.example.dao;

import org.example.dto.Tax;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface TaxListDao {
    void loadLibrary() throws FileNotFoundException;

    ArrayList<Tax> getAllTaxes();
}
