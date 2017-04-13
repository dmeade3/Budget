package user.accounts;

import static user.accounts.AccountType.RETIREMENT;

/**
 * Created by dcmeade on 4/13/2017.
 */
public class RetirementAccount extends Account
{
    public RetirementAccount(String name)
    {
        super(name, RETIREMENT);
    }
}
