package user.budget;

/**
 * Created by dcmeade on 4/13/2017.
 */
public enum BudgetCategory
{
    ELECTRICITY("Electricity"),
    GASOLINE("Gasoline"),
    NATURAL_GAS("Natural Gas"),
    INTERNET("Internet"),
    PHONE("Phone"),
    RENT("Rent"),
    WATER("Water"),
    RESTAURANTS("Restaurants"),
    GROCERIES("Groceries")

    ;

    String name;

    BudgetCategory(String name)
    {
        this.name = name;
    }
}
