package dev_test_area;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import user.budget.Budget;

/**
 * Project: Budget
 * File Name: Test.java
 * <p>
 * Created by David on 4/9/2017.
 */
public class Test extends Application
{

	@Override
	public void start(Stage stage)
	{
		///BudgetWithProgressBar budgetWithProgressBar = new BudgetWithProgressBar();

		Scene scene = new Scene(new BudgetWithProgressBar(new Budget("Muh budget", 344, 100)), 500, 150);

		stage.setScene(scene);
		stage.setTitle("Test");

		stage.show();
	}


	// Start the whole application
	public static void main(String[] args)
	{
		BasicConfigurator.configure();

		launch(args);
	}
}
