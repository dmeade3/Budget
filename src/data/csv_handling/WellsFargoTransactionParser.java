package data.csv_handling;

import com.opencsv.CSVReader;
import data.csv_handling.transaction_handling.Transaction;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static data.MainProgramDatastore.getTransactionColumnIndex;

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

            //Read CSV line by line and use the string array as you want
            for(String[] row : allRows)
            {
                if (row.length > 1)
                {
                    int checkNumber = 0;

                    if (!row[getTransactionColumnIndex("checkNumber")].equals(""))
                    {
                        checkNumber = Integer.parseInt(row[2]);
                    }

                    transactions.add(new Transaction(new Date(row[getTransactionColumnIndex("name")]),
                            Double.valueOf(row[getTransactionColumnIndex("amount")]),
                            checkNumber,
                            row[getTransactionColumnIndex("description")],
                            "",
                            "")); // TODO this is going to give nulls
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
	    BasicConfigurator.configure();

        WellsFargoTransactionParser wellsFargoTransactionParser = new WellsFargoTransactionParser("C:\\Users\\David\\Desktop\\Intelij Workspace\\Budget\\test_input_files\\Checking1.csv");

        List<Transaction> transactions = wellsFargoTransactionParser.parseTransactions();

        for (Transaction transaction : transactions)
        {
            System.out.println(transaction);
        }
    }
}
