package banking;

import java.sql.*;

public class Database {
//    private final static String PATH = "jdbc:sqlite:";
     private final static String PATH = "jdbc:sqlite:C:\\Users\\Korisnik\\IdeaProjects\\Simple Banking System\\";
    private static Connection conn;
    private static Statement stmt;
    private final static String TABLE = "card";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NUMBER = "number";
    private final static String COLUMN_PIN = "pin";
    private final static String COLUMN_BALANCE = "balance";

    public Database(String dbName) {
        try {
            Connection conn = DriverManager.getConnection(PATH + dbName);
            stmt = conn.createStatement();
//            stmt.execute("DROP TABLE IF EXISTS " + TABLE);
            stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NUMBER + " TEXT," +
                    COLUMN_PIN + " TEXT," +
                    COLUMN_BALANCE + " INTEGER DEFAULT 0)");

        } catch (SQLException e) {
            System.out.println("DATABASE ERROR: " + e.getMessage());
        }
    }

    public static void createAccount(String cardNumber, String pinCode) {
        try {
            String sql = "INSERT INTO " + TABLE + "(number, pin) VALUES('" +
                    cardNumber + "', '" + pinCode + "')";
            stmt.execute(sql);
            System.out.println("\nYour card has been created");
            System.out.println("Your card number:\n" + cardNumber);
            System.out.println("Your card PIN:\n" + pinCode + "\n");
        } catch (SQLException e) {
            System.out.println("Wrong insert: " + e.getMessage());
        }
    }

    public static Customer logUser(String cardNumber, String pinCode) {
        Customer customer = null;
        try {
            String sql = "SELECT * FROM " + TABLE + " WHERE " +
                    COLUMN_NUMBER + " = '" + cardNumber + "' AND " +
                    COLUMN_PIN + " = '" + pinCode + "'";

            String sada = "SELECT * FROM cards WHERE" +
                    "number = '1231231' AND pin = '213132";

            ResultSet user = stmt.executeQuery(sql);
            while (user.next()) {
                customer = new Customer(user.getString(COLUMN_NUMBER), user.getString(COLUMN_PIN),
                        true, user.getInt(COLUMN_BALANCE));
            }
        } catch (SQLException e) {
            System.out.println("Getting records: " + e.getMessage());
        }
        return customer;
    }

    public static void closeConnection() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}