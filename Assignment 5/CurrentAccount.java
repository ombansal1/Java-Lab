public class CurrentAccount extends Account {
    private double minimumBalance = 500;

    public CurrentAccount(int accountNumber, double balance, Customer customer) {
        super(accountNumber, balance, customer);
    }

    public void checkMinimumBalance() {
        if (balance < minimumBalance)
            System.out.println("Minimum balance not maintained");
        else
            System.out.println("Minimum balance maintained");
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance)
            throw new InsufficientBalanceException("Insufficient Balance");

        balance -= amount;
        System.out.println("Current Withdrawal: " + amount);
    }
}
