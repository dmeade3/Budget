package user;

import com.opencsv.CSVReader;
import data.MainProgramDatastore;
import data.csv_handling.transaction_handling.Transaction;
import gui.components.BudgetWithProgressBar;
import org.apache.log4j.Logger;
import user.accounts.*;
import user.budget.BudgetSection;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static data.MainProgramDatastore.getColumnIndex;
import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Project: Budget
 * File Name: User.java
 * <p>
 * Created by David on 4/9/2017.
 */
public class User
{
	Logger logger = Logger.getLogger(User.class.getName());

	private String name;
	private List<BudgetWithProgressBar> budgets;
	private List<Account> accounts;
	private List<Transaction> transactions;

	public User(String name)
	{
		this.name = name;
		this.transactions = new ArrayList<>();
		this.accounts = new ArrayList<>();
		this.budgets = new ArrayList<>();


		readInTransactions();
		readInAccounts();
		readInBudget();
	}

	public String getName()
	{
		return name;
	}

	public List<Account> getAccounts()
	{
		return accounts;
	}

	public List<Transaction> getTransactions()
	{
		List<Transaction> filteredTransactions;

		filteredTransactions = dateFilterTransactions(MainProgramDatastore.getInstance().getStartDate(), MainProgramDatastore.getInstance().getEndDate(), transactions);

		return filteredTransactions;
	}

	private List dateFilterTransactions(Date startDate, Date endDate, List<Transaction> inTransactions)
	{
		ArrayList filteredTransactions = new ArrayList();

		for (Transaction transaction : inTransactions)
		{
			if ((startDate.getTime() <= transaction.getDate().getTime()) &&
				(endDate.getTime() >= transaction.getDate().getTime()))
			{
				filteredTransactions.add(transaction);
			}
		}

		return filteredTransactions;
	}

	public List<BudgetWithProgressBar> getBudgets()
	{
		return budgets;
	}

	private void readInTransactions()
	{
		transactions.clear();

		// Get all file names under dir
		File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\transactions.csv");

		try (CSVReader reader = new CSVReader(new FileReader(file), ',', '"', 1))
		{
			//Read all rows at once
			List<String[]> allRows = reader.readAll();

			//Read CSV line by line and use the string array as you want
			for (String[] row : allRows)
			{
				// make sure row exists and matches type for section
				if (row.length > 1)
				{
					int checkingNumber = 0;

					// Handle no checking number
					if (!row[getColumnIndex("checkNumber", "transactions.csv")].equals(""))
					{
						checkingNumber = Integer.valueOf(row[getColumnIndex("checkNumber", "transactions.csv")]);
					}

					transactions.add(new Transaction(
							new Date(row[getColumnIndex("date", "transactions.csv")]),
							Double.valueOf(row[getColumnIndex("amount", "transactions.csv")]),
							checkingNumber,
							row[getColumnIndex("description", "transactions.csv")],
							row[getColumnIndex("account", "transactions.csv")],
							row[getColumnIndex("category", "transactions.csv")]));
				}
			}
		} catch (IOException e)
		{
			// TODO logger.warn("Could not open file: " + filename);
		}
	}

	private void readInAccounts()
	{
		try(CSVReader reader = new CSVReader(new FileReader(USERS_PATH + "\\" + CURRENT_USER + "\\accounts.csv"), ',' , '"' , 1))
		{
			//Read all rows at once
			List<String[]> allRows = reader.readAll();

			//Read CSV line by line and use the string array as you want
			for(String[] row : allRows)
			{
				// make sure row exists and matches type for section
				if (row.length > 1)
				{
					switch(AccountType.valueOf(row[getColumnIndex("type", "accounts.csv")].toUpperCase()))
					{
						case CHECKING:
							accounts.add(new CheckingAccount(row[getColumnIndex("name", "accounts.csv")]));
							break;

						case BROKERAGE:
							accounts.add(new BrokerageAccount(row[getColumnIndex("name", "accounts.csv")]));
							break;

						case CREDIT:
							accounts.add(new CreditAccount(row[getColumnIndex("name", "accounts.csv")]));
							break;

						case RETIREMENT:
							accounts.add(new RetirementAccount(row[getColumnIndex("name", "accounts.csv")]));
							break;

						case SAVINGS:
							accounts.add(new SavingsAccount(row[getColumnIndex("name", "accounts.csv")]));
							break;

						default:
							// TODO error message
					}
				}
			}
		}
		catch (IOException e)
		{
			// TODO logger.warn("Could not open file: " + filename);
		}
	}

	private void readInBudget()
	{
		try(CSVReader reader = new CSVReader(new FileReader(USERS_PATH + "\\" + CURRENT_USER + "\\budget.csv"), ',' , '"' , 1))
		{
			//Read all rows at once
			List<String[]> allRows = reader.readAll();

			//Read CSV line by line and use the string array as you want
			for(String[] row : allRows)
			{
				// make sure row exists
				if (row.length > 1)
				{
					budgets.add(new BudgetWithProgressBar(new BudgetSection(row[getColumnIndex("category", "budget.csv")], Integer.valueOf(row[getColumnIndex("budgetLimit", "budget.csv")]))));
				}
			}
		}
		catch (IOException e)
		{
			logger.warn("Could not open budget.csv file, now creating a new budget.csv");

			File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\budget.csv");

			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					logger.warn("New budget.csv was not able to be created: " + e.getMessage());
				}
			}
		}
	}
}
