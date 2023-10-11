package banking;

public class LoggedBankAccount extends BankAccount {
    public LoggedBankAccount(double currentBalance) throws Exception {
        super(currentBalance);
    }

    /**
    * @preconditions amount > 0
    * @param amount of money wanted to be deposited
    * @postcondition balance >= 0 && balance = balance + amount
    */
    @Override
    public void deposits(double amount) {
        System.out.println("deposit: " + amount);
        super.deposits(amount);
    }

    /**
     * @precondition amount > 0 and balance > 0
     * @param amount of money withdrawn from bank
     * postconditions balacne amount >= 0 && balance = balance - amount
    */
    @Override
    public void withdraw(double amount) {
        if (amount == 0) {
            System.out.println("fail");
        }
        System.out.println("withdraw: " + amount);
        super.withdraw(amount);
    }
}
