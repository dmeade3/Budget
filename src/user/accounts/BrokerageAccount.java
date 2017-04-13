package user.accounts;

import static user.accounts.AccountType.BROKERAGE;

/**
 * Created by dcmeade on 4/13/2017.
 */
public class BrokerageAccount extends Account
{
    public BrokerageAccount(String name)
    {
        super(name, BROKERAGE);
    }
}
