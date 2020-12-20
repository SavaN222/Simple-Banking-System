package com.sava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final static String PATH = "jdbc:sqlite:";
    private Connection conn;
    private Statement stmt;
    private final static String TABLE = "cards";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NUMBER = "number";
    private final static String COLUMN_PIN = "pin";
    private final static String COLUMN_BALANCE = "balance";

    public Database(String dbName) {
        try {
            Connection conn = DriverManager.getConnection(PATH + dbName);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR: " + e.getMessage());
        }
    }

}
