package data.csv_handling;

import user.budget.BudgetSection;
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
	public static void writeOut(List<BudgetSection> budgetSections, String fileOutPath)
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

			bufferedWriter.write(BudgetSection.csvHeader());

			for (BudgetSection budgetSection : budgetSections)
			{
				bufferedWriter.write(budgetSection.csvToString());
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
		/*List<BudgetSection> budgetSections = new ArrayList<>();
		budgetSections.add(new BudgetSection("Budget item 1", 123));
		budgetSections.add(new BudgetSection("Budget item 2", 123));
		budgetSections.add(new BudgetSection("Budget item 3", 123));
		budgetSections.add(new BudgetSection("Budget item 4", 123));*/


		//writeOut(budgetSections, SystemInfo.USERS_PATH + "\\" + CURRENT_USER + "\\budget.csv");
	}
}
