public class SavingsAccount extends Account {
    private double interestRate = 0.06;

    public SavingsAccount(int accountNumber, double balance, Customer customer) {
        super(accountNumber, balance, customer);
    }

    public void calculateInterest() {
        double interest = balance * interestRate;
        System.out.println("Interest: " + interest);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (balance - amount < 500)
            throw new InsufficientBalanceException("Minimum balance 500 required");

        balance -= amount;
        System.out.println("Savings Withdrawal: " + amount);
    }
}
