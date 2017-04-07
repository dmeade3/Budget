package transaction_handling;

import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class WellsFargoTransactionParser
{
    private static final Logger logger = Logger.getLogger(WellsFargoTransactionParser.class.getName());

    private String filename;

    public WellsFargoTransactionParser(String filename)
    {
        this.filename = filename;
    }

    public List<Transaction> parseTransactions()
    {
        List<Transaction> transactions = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(filename), ',' , '"' , 0))
        {
            //Read all rows at once
            List<String[]> allRows = reader.readAll();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

            //Read CSV line by line and use the string array as you want
            for(String[] row : allRows)
            {
                if (row.length > 1)
                {
                    int checkNumber = -1;

                    if (!row[3].equals(""))
                    {
                        checkNumber = Integer.parseInt(row[3]);
                    }

                    transactions.add(new Transaction(simpleDateFormat.format(new Date(row[0])), Double.valueOf(row[1]), row[2], checkNumber, row[4]));
                }
            }
        }
        catch (IOException e)
        {
            logger.warn("Could not open file: " + filename);
        }

        return transactions;
    }


    public static void main(String... args)
    {
        WellsFargoTransactionParser wellsFargoTransactionParser = new WellsFargoTransactionParser("C:\\Users\\dcmeade\\Desktop\\Intelij Workspace\\Budget\\test_input_files\\Checking1.csv");

        List<Transaction> transactions = wellsFargoTransactionParser.parseTransactions();

        for (Transaction transaction : transactions)
        {
            System.out.println(transaction);
        }
    }
}
