package user.budget;

/**
 * Project: Budget
 * File Name: Budget.java
 * <p>
 * Created by David on 4/9/2017.
 */
public class Budget
{
	String name;
	private int budgetLimit;
	private double amountSpent;

	public Budget(String name, int budgetLimit, double amountSpent)
	{
		this.name = name;
		this.budgetLimit = budgetLimit;
		this.amountSpent = amountSpent;
	}

	public String getName()
	{
		return name;
	}

	public int getBudgetLimit()
	{
		return budgetLimit;
	}

	public double getAmountSpent()
	{
		return amountSpent;
	}
}
