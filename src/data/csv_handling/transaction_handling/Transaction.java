package data.csv_handling.transaction_handling;

import user.budget.BudgetCategory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.SEPARATOR;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class Transaction
{
    private Date date;
    private Double amount;
    private int checkNumber;
    private String description;
    private String accountName;
    private BudgetCategory category;

    public static DateFormat dateFormat =  new SimpleDateFormat("MM/dd/yyyy");
    public static NumberFormat formatter = new DecimalFormat("#0.00");

    public Transaction(Date date, Double amount, int checkNumber, String description, String accountName, BudgetCategory category)
    {
        this.date = date;
        this.amount = amount;
        this.checkNumber = checkNumber;
        this.description = description;
        this.accountName = accountName;
        this.category = category;
    }

    public BudgetCategory getCategory()
    {
        return category;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public Date  getDate()
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

    public boolean equals(Transaction inTransaction)
    {
        if (dateFormat.format(this.date).equals(dateFormat.format(inTransaction.getDate())) &&
            formatter.format(amount).equals(formatter.format(inTransaction.amount)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String transactionEditorString()
    {
        return date + SEPARATOR + formatter.format(amount) + SEPARATOR + "unknown" + SEPARATOR + checkNumber +
                SEPARATOR + description + SEPARATOR + accountName + SEPARATOR + category;
    }

    public void writeOutTransaction()
    {
        String checkNumber = String.valueOf(getCheckNumber());

        if (Integer.valueOf(getCheckNumber()).equals(0))
        {
            checkNumber = "";
        }

        String newTransaction = "\"" + Transaction.dateFormat.format(getDate()) + "\",\"" +
                Transaction.formatter.format(getAmount()) + "\",\"" +
                checkNumber + "\",\"" +
                getDescription() + "\",\"" +
                "\",\"" +   // Account at this point is not read in or mapped
                category + "\"" // Category at this point is not mapped
                ;

        File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\transactions.csv");
        try
        {
            Path path = Paths.get(String.valueOf(file));
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(path), charset);

            content = content + "\n" + newTransaction;

            Files.write(path, content.getBytes(charset));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
