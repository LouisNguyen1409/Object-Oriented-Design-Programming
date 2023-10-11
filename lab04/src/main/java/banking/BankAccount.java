package banking;

public class BankAccount {
    private double balance;

    public BankAccount(double currentBalance) throws Exception {
        if (currentBalance < 0)
            throw new Exception("Balance must be >= 0");
        balance = currentBalance;
    }

    /**
     * @preconditions amount > 0
     * @param amount of money wanted to be deposited
     * @postcondition balance >= 0 && balance = balance + amount
    */
    public void deposits(double amount) {
        balance += amount;
    }

    /**
     * @precondition amount > 0 and balance > 0
     * @param amount of money withdrawn from bank
     * postconditions balacne amount >= 0 && balance = balance - amount
    */
    public void withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
        }
    }
}
