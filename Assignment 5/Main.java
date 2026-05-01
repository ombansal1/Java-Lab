import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Customer> customers = new ArrayList<>();

        Customer c1 = new Customer("1", "A", "B", "a@mail.com",
                "1111111111", "City", "123", "PAN1");

        Customer c2 = new Customer("2", "C", "D", "b@mail.com",
                "2222222222", "City", "456", "PAN2");

        customers.add(c1);
        customers.add(c2);

        ArrayList<Account> accounts = new ArrayList<>();

        SavingsAccount sa1 = new SavingsAccount(1001, 60000, c1);
        CurrentAccount ca1 = new CurrentAccount(2001, 35000, c1);

        accounts.add(sa1);
        accounts.add(ca1);

        System.out.println("Transactions");

        sa1.deposit(5000);

        try {
            sa1.withdraw(2000);
        } catch (InsufficientBalanceException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }

        sa1.calculateInterest();

        Loan loan = new Loan(24000, 24);
        loan.displayLoan();

        try {
            loan.payEMI(sa1);
        } catch (InsufficientBalanceException e) {
            System.out.println("EMI payment failed: " + e.getMessage());
        }

        try {
            sa1.withdraw(1_000_000);
        } catch (InsufficientBalanceException e) {
            System.out.println("Expected withdrawal exception: " + e.getMessage());
        }

        Loan bigLoan = new Loan(1000000, 1); // EMI = 1,000,000
        bigLoan.displayLoan();
        try {
            bigLoan.payEMI(ca1);
        } catch (InsufficientBalanceException e) {
            System.out.println("Expected EMI exception: " + e.getMessage());
        }

        displayInfo(customers, accounts);
    }

    public static void displayInfo(ArrayList<Customer> customers, ArrayList<Account> accounts) {
        for (Customer cust : customers) {
            cust.displayCustomer();

            for (Account acc : accounts) {
                if (acc.customer.customerID.equals(cust.customerID)) {
                    System.out.println("Account: " + acc.accountNumber);
                    System.out.println("Type: " + acc.getClass().getSimpleName());
                    System.out.println("Balance: " + acc.balance);
                }
            }
        }
    }
}
