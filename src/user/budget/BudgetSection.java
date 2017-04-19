package user.budget;

/**
 * Project: Budget
 * File Name: Budget.java
 * <p>
 * Created by David on 4/9/2017.
 */
public class BudgetSection
{
	String name;
	private int budgetLimit;
	private double amountSpent;

	public BudgetSection(String name, int budgetLimit)
	{
		this.name = name;
		this.budgetLimit = budgetLimit;
	}

	public void setAmountSpent(double amountSpent)
	{
		this.amountSpent = amountSpent;
	}

	@Override
	public String toString()
	{
		return "Budget{" +
				"name='" + name + '\'' +
				", budgetLimit=" + budgetLimit +
				", amountSpent=" + amountSpent +
				'}';
	}

	public String csvToString()
	{
		return "\"" + name + "\",\"" + budgetLimit + "\n";
	}

	public static String csvHeader()
	{
		return "name,budgetLimit,amountSpent\n";
	}

	public String getName()
	{
		return name;
	}

	public int getBudgetSectionLimit()
	{
		return budgetLimit;
	}

	public double getAmountSpent()
	{
		return amountSpent;
	}
}
