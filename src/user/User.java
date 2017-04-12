package user;

import user.accounts.Account;
import user.budget.BudgetSection;

import java.util.List;

/**
 * Project: Budget
 * File Name: User.java
 * <p>
 * Created by David on 4/9/2017.
 */
public class User
{
	private String name;
	private List<BudgetSection> budgetSections;
	private List<Account> accounts;


	public User(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
