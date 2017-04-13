package user.accounts;

import static user.accounts.AccountType.CREDIT;

/**
 * Created by dcmeade on 4/13/2017.
 */
public class CreditAccount extends Account
{
    public CreditAccount(String name)
    {
        super(name, CREDIT);
    }
}
