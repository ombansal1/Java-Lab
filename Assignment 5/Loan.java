public class Loan {
    private double loanAmount;
    private int months;
    private double emi;

    public Loan(double loanAmount, int months) {
        this.loanAmount = loanAmount;
        this.months = months;
        this.emi = loanAmount / months;
    }

    public void payEMI(Account acc) throws InsufficientBalanceException {
        if (acc.balance < emi)
            throw new InsufficientBalanceException("Insufficient balance for EMI");

        acc.balance -= emi;
        System.out.println("EMI Paid: " + emi);
        System.out.println("Remaining Balance: " + acc.balance);
    }

    public void displayLoan() {
        System.out.println("Loan Amount: " + loanAmount);
        System.out.println("EMI: " + emi);
    }
}
