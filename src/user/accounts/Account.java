package user.accounts;

/**
 * Project: Budget
 * File Name: Account.java
 * <p>
 * Created by David on 4/9/2017.
 */
public abstract class Account
{

    // TODO make this a super class or interface that other account types can be created from

    protected String name;
    protected AccountType accountType;

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
}
