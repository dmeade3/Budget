package data.csv_handling;

import com.opencsv.CSVReader;
import data.csv_handling.transaction_handling.HeaderFields;
import data.csv_handling.transaction_handling.Transaction;
import data.csv_handling.transaction_handling.TransactionSource;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static data.MainProgramDatastore.getColumnIndex;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class WellsFargoTransactionParser
{
    private static final Logger logger = Logger.getLogger(WellsFargoTransactionParser.class.getName());


    /*
    *
    * TODO Depricated
    *
    * */

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
                if (row[getColumnIndex("date", "transactions.csv")].equals("date"))
                {
                    continue;
                }

                if (row.length > 1)
                {
                    int checkNumber = 0;

                    if (!row[getHeaderIndex("checkNumber")].equals(""))
                    {
                        checkNumber = Integer.parseInt(row[getHeaderIndex("checkNumber")]);
                    }

                    transactions.add(new Transaction(new Date(row[getHeaderIndex("date")]),
                            Double.valueOf(row[getHeaderIndex("amount")]),
                            checkNumber,
                            row[getHeaderIndex("description")],
                            "",
                            ""));
                }
            }
        }
        catch (IOException e)
        {
            logger.warn("Could not open file: " + filename);
        }

        return transactions;
    }

    private int getHeaderIndex(String target)
    {
        int ctr = 0;
        for (HeaderFields headerFields : TransactionSource.WELLSFARGOCHECKING.getHeader())
        {
            if (headerFields.getName().equals(target))
            {
                return ctr;
            }

            ctr++;
        }

        return -1;
    }


    /*public static void main(String... args)
    {
	    BasicConfigurator.configure();

        WellsFargoTransactionParser wellsFargoTransactionParser = new WellsFargoTransactionParser("C:\\Users\\David\\Desktop\\Intelij Workspace\\Budget\\test_input_files\\Checking1.csv");

        List<Transaction> transactions = wellsFargoTransactionParser.parseTransactions();

        for (Transaction transaction : transactions)
        {
            System.out.println(transaction);
        }
    }*/
}
