import java.util.ArrayList;

public class Account {
    protected int accountNumber;
    protected double balance;
    protected Customer customer;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    public Account(int accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount + ", Balance: " + balance);
        System.out.println("Deposited: " + amount);
        System.out.println("Balance: " + balance);
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance)
            throw new InsufficientBalanceException("Insufficient Balance");

        balance -= amount;
        transactionHistory.add("Withdrawn: " + amount + ", Balance: " + balance);
        System.out.println("Withdrawn: " + amount);
        System.out.println("Balance: " + balance);
    }

    public void displayAccount() {
        customer.displayCustomer();
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
    }

    public void printTransactionHistory() {
        for (String t : transactionHistory) {
            System.out.println(t);
        }
    }
}
