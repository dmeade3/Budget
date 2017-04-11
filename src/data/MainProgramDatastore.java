package data;

import com.opencsv.CSVReader;
import data.csv_handling.transaction_handling.Transaction;
import user.accounts.Account;
import user.budget.BudgetSection;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class MainProgramDatastore
{
    // Create the instance of the object
    private static MainProgramDatastore ourInstance = new MainProgramDatastore();

    private List<Account> accountList;
    private List<BudgetSection> budgetSectionList;
    private List<Transaction> transactionsList;
    private List<String> userNames;


    private MainProgramDatastore()
    {
        accountList = new ArrayList<>();
        budgetSectionList = new ArrayList<>();
        transactionsList  = new ArrayList<>();
        userNames  = new ArrayList<>();
    }

    public static MainProgramDatastore getInstance()
    {
        return ourInstance;
    }

    public List<Transaction> getTransactionsList()
    {
        return transactionsList;
    }

    public List<Account> getAccountList()
    {
        return accountList;
    }

    public List<BudgetSection> getBudgetSectionList()
    {
        return budgetSectionList;
    }

    public List<String> getUserNames()
    {
        return userNames;
    }

    public void readInAccounts(String filename)
    {

    }

    public void readInBudget(String filename)
    {

    }

    // Takes Dir
    public void readInUserNames()
    {
        // Get all file names under dir
        File folder = new File(USERS_PATH);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isDirectory())
            {
                // TODO turn these prints into logging
                userNames.add(listOfFiles[i].getName());
            }
        }
    }

    // Takes Dir
    public void readInTransactions(String dirName)
    {
		transactionsList = new ArrayList<>();

        // Get all file names under dir
        File folder = new File(dirName);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                // TODO turn these prints into logging
                //System.out.println("File read in: " + listOfFiles[i].getName());

                // TODO have better way to point to this file in the future
                try(CSVReader reader = new CSVReader(new FileReader(listOfFiles[i]), ',' , '"' , 0))
                {
                    //Read all rows at once
                    List<String[]> allRows = reader.readAll();

                    //Read CSV line by line and use the string array as you want
                    for(String[] row : allRows)
                    {
                        // make sure row exists and matches type for section
                        if (row.length > 1)
                        {
                            int checkingNumber = 0;

                            // Handle no checking number
                            if (!row[3].equals(""))
                            {
                                checkingNumber = Integer.valueOf(row[3]);
                            }

                            transactionsList.add(new Transaction(row[0], Double.valueOf(row[1]), row[2], checkingNumber, row[4]));
                        }
                    }
                }
                catch (IOException e)
                {
                    // TODO logger.warn("Could not open file: " + filename);
                }
            }
            else if (listOfFiles[i].isDirectory())
            {
                // TODO turn these prints into logging
                System.out.println("Directory read in: " + listOfFiles[i].getName());
            }
        }
    }
}
