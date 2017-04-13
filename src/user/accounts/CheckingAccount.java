package user.accounts;

import static user.accounts.AccountType.CHECKING;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class CheckingAccount extends Account
{

    public CheckingAccount(String name)
    {
        super(name, CHECKING);


    }
}
