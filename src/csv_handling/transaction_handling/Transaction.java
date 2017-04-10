package csv_handling.transaction_handling;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

    NumberFormat formatter = new DecimalFormat("#0.00");

    public static String header = "Date,         Amount, Mystery, Check Number, Description";

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

    public String toCsvString = "\"" +  date + "\",\"" +  formatter.format(amount) + "\",\"" + mystery + "\",\"" +  checkNumber + "\",\"" +  description + "\"\n";

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

    public String guiViewString()
    {
        return date + "," + formatter.format(amount) + "," + mystery + "," + checkNumber + "," + description;
    }
}
