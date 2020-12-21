package banking;

public class Customer {
    private String cardNumber;
    private String pinCode;
    private boolean isLoggedIn;
    private int balance;

    public Customer(String cardNumber, String pinCode, boolean isLoggedIn, int balance) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.isLoggedIn = isLoggedIn;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}