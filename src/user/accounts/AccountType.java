package user.accounts;

/**
 * Created by dcmeade on 4/10/2017.
 */
public enum AccountType
{
    CHECKING("Checking"),
    SAVINGS("Savings"),
    BROKERAGE("Brokerage"),
    CREDIT("Credit"),
    RETIREMENT("Retirement")      // Eventually might split up to 401k, Roth ira, ira, stock
    ;

    private final String name;

    AccountType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
