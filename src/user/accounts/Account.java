package user.accounts;

/**
 * Project: Budget
 * File Name: Account.java
 * <p>
 * Created by David on 4/9/2017.
 */
public abstract class Account
{
    protected String name;
    protected AccountType accountType;
    protected double balance;

    Account(String name, AccountType accountType)
    {
        this.name = name;
        this.accountType = accountType;
    }

    public AccountType getAccountType()
    {
        return accountType;
    }

    public String getName()
    {
        return name;
    }

    public double getBalance()
    {
        return balance;
    }

    protected void setBalance(double balance)
    {
        this.balance = balance;
    }
}
