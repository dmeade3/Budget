package user.accounts;

import static user.accounts.AccountType.SAVINGS;

/**
 * Created by dcmeade on 4/13/2017.
 */
public class SavingsAccount extends Account
{
    public SavingsAccount(String name)
    {
        super(name, SAVINGS);
    }
}
