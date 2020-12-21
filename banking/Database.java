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

    /**
     * Create database connection and create table.
     * @param dbName take input from command line
     */
    public Database(String dbName) {
        try {
            conn = DriverManager.getConnection(PATH + dbName);
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NUMBER + " TEXT," +
                    COLUMN_PIN + " TEXT," +
                    COLUMN_BALANCE + " INTEGER DEFAULT 0)");
            stmt.close();

        } catch (SQLException e) {
            System.out.println("DATABASE ERROR: " + e.getMessage());
        }
    }

    /**
     * Create customer account
     * @param cardNumber random generate 16-card number
     * @param pinCode random generate 4-pin code
     */
    public static void createAccount(String cardNumber, String pinCode) {
        String sql = "INSERT INTO " + TABLE + " (number, pin) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pinCode);

            preparedStatement.executeUpdate();

            System.out.println("\nYour card has been created");
            System.out.println("Your card number:\n" + cardNumber);
            System.out.println("Your card PIN:\n" + pinCode + "\n");
        } catch (SQLException e) {
            System.out.println("Insert ERROR: " + e.getMessage());
        }
    }

    /**
     * Add income and update customer balance
     * @param income userInput income
     * @param cardNumber cardNumber of login user
     * @throws SQLException
     */
    public static void addIncome(int income, String cardNumber) throws SQLException {
        String sql = "UPDATE " + TABLE + " SET " +
                COLUMN_BALANCE + " = " + COLUMN_BALANCE + " + ?" +
                " WHERE " + COLUMN_NUMBER + " = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, income);
            preparedStatement.setString(2, cardNumber);

            preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete user account
     * @param cardNumber delete login user account
     * @throws SQLException
     */
    public static void deleteAccount(String cardNumber) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE " +
                COLUMN_NUMBER + " = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Check if account exist
     * @param cardNumber user input acc Number of another customer
     * @return string cardNumber
     */
    public static String accountExists(String cardNumber) {
        String sql = "SELECT number FROM " + TABLE + " WHERE " +
                COLUMN_NUMBER + " = ? ";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet result = preparedStatement.executeQuery();
            return result.getString(COLUMN_NUMBER);
        } catch (SQLException e) {
            return "";
        }
    }

    /**
     * Transaction for transfering money
     * @param senderCardNumber
     * @param recipientCardNumber
     * @param amount
     * @throws SQLException
     */
    public static void transferMoney(String senderCardNumber, String recipientCardNumber, int amount) throws SQLException {
        conn.setAutoCommit(false);
        addIncome(-amount, senderCardNumber);
        addIncome(amount, recipientCardNumber);
        conn.commit();

    }

    /**
     * Log user to bank account
     * @param cardNumber user string input
     * @param pinCode user string input
     * @return customer object with data for that customer
     */
    public static Customer logUser(String cardNumber, String pinCode) {
        Customer customer = null;

        String sql = "SELECT * FROM " + TABLE + " " +
                "WHERE " +
                COLUMN_NUMBER + " = ? AND " +
                COLUMN_PIN + " = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pinCode);

            ResultSet user = preparedStatement.executeQuery();
            while (user.next()) {
                customer = new Customer(user.getString(COLUMN_NUMBER), user.getString(COLUMN_PIN),
                        true, user.getInt(COLUMN_BALANCE));
            }
        } catch (SQLException e) {
            System.out.println("Getting records: " + e.getMessage());
        }
        return customer;
    }

    /**
     * Close connection
     */
    public static void closeConnection() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}