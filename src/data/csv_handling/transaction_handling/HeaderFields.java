package data.csv_handling.transaction_handling;

/**
 * Created by dcmeade on 4/18/2017.
 */
public enum HeaderFields
{
    DATE("date"),
    AMOUNT("amount"),
    UNKNOWN("unknown"),
    CHECKNUMBER("checkNumber"),
    DESCRIPTION("description"),
    CATEGORY("category"),
    ACCOUNT("account")
    ;

    private String name;

    HeaderFields(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
