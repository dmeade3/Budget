package csv_handling;

import sun.plugin2.util.SystemUtil;
import user.budget.Budget;
import util.SystemInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static util.SystemInfo.CURRENT_USER;

/**
 * Project: Budget
 * File Name: BudgetWriteOutCsv.java
 * <p>
 * Created by David on 4/9/2017.
 */
public class BudgetWriteOutCsv
{
	public static void writeOut(List<Budget> budgets, String fileOutPath)
	{
		BufferedWriter bufferedWriter = null;
		try
		{
			File myFile = new File(fileOutPath);

			// check if file exist, otherwise create the file before writing
			if (!myFile.exists())
			{
				myFile.createNewFile();
			}
			Writer writer = new FileWriter(myFile);
			bufferedWriter = new BufferedWriter(writer);

			bufferedWriter.write(Budget.csvHeader());

			for (Budget budget : budgets)
			{
				bufferedWriter.write(budget.csvToString());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(bufferedWriter != null)
				{
					bufferedWriter.close();
				}
			}
			catch(Exception ex)
			{
			}
		}
	}

	public static void main(String... args)
	{
		List<Budget> budgets = new ArrayList<>();
		budgets.add(new Budget("Budget item 1", 123, 123));
		budgets.add(new Budget("Budget item 2", 123, 123));
		budgets.add(new Budget("Budget item 3", 123, 123));
		budgets.add(new Budget("Budget item 4", 123, 123));


		writeOut(budgets, SystemInfo.USERS_PATH + "\\" + CURRENT_USER + "\\budget.csv");
	}
}
