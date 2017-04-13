package data.csv_handling.transaction_handling;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class Transaction
{
    private String date;
    private Double amount;
    private int checkNumber;
    private String description;
    private String accountName;
    private String category;

    public static NumberFormat formatter = new DecimalFormat("#0.00");

    public Transaction(String date, Double amount, int checkNumber, String description, String accountName, String category)
    {
        this.date = date;
        this.amount = amount;
        this.checkNumber = checkNumber;
        this.description = description;
        this.accountName = accountName;
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }

    public String  getDate()
    {
        return date;
    }

    public Double getAmount()
    {
        return amount;
    }

    public int getCheckNumber()
    {
        return checkNumber;
    }

    public String getDescription()
    {
        return description;
    }

    //public String toCsvString = "\"" +  date + "\",\"" +  formatter.format(amount) + "\",\"" + "\"\"" + "\",\"" +  checkNumber + "\",\"" +  description + "\"\n";

    @Override
    public String toString()
    {
        return "Transaction { " +
                "date=" + date +
                ", amount=" + amount +
                ", checkNumber=" + checkNumber +
                ", description='" + description + '\'' +
                " }";
    }

    public String guiViewString()
    {
        return date + "," + formatter.format(amount) + "," + "\"\"" + "," + checkNumber + "," + description;
    }
}
