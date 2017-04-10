package csv_handling.transaction_handling;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class Transaction
{
    private String date;
    private double amount;
    private String mystery;
    private int checkNumber;
    private String description;

    public Transaction(String date, double amount, String mystery, int checkNumber, String description)
    {
        this.date = date;
        this.amount = amount;
        this.mystery = mystery;
        this.checkNumber = checkNumber;
        this.description = description;
    }


    public String getDate()
    {
        return date;
    }

    public double getAmount()
    {
        return amount;
    }

    public String getMystery()
    {
        return mystery;
    }

    public int getCheckNumber()
    {
        return checkNumber;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return "Transaction { " +
                "date=" + date +
                ", amount=" + amount +
                ", mystery='" + mystery + '\'' +
                ", checkNumber=" + checkNumber +
                ", description='" + description + '\'' +
                " }";
    }
}
