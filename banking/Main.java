package banking;

public class Main {

    public static void main(String[] args) {
        if (args[0].equals("-fileName") && args[1] != null) {
            Database db = new Database(args[1]);
        } else {
            System.out.println("Database name is not supplied. \nExiting the program.");
        }
        Bank bank = new Bank();

    }
}