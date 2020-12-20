package com.sava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
//    private final static String PATH = "jdbc:sqlite:";
    private final static String PATH = "jdbc:sqlite:C:\\Users\\Korisnik\\IdeaProjects\\Simple Banking System\\";
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
            stmt.execute("DROP TABLE IF EXISTS " + TABLE);

            stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NUMBER + " TEXT," +
                    COLUMN_PIN + " TEXT," +
                    COLUMN_BALANCE + " INTEGER DEFAULT 0)");

        } catch (SQLException e) {
            System.out.println("DATABASE ERROR: " + e.getMessage());
        }
    }

    public void createAccount(String cardNumber, String pinCode) {

    }

}
