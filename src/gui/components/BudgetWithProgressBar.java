package gui.components;

import gui.GUIMain;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.apache.log4j.Logger;
import user.budget.BudgetSection;
import util.SystemInfo;

public class BudgetWithProgressBar extends GridPane
{
	private static final Logger logger = Logger.getLogger(GUIMain.class.getName());
	private final GridPane budgetVisualRoot;

	private BudgetSection budgetSection;

	public BudgetWithProgressBar(BudgetSection budgetSection)
	{
		this.budgetSection = budgetSection;

		budgetVisualRoot = this;
		//budgetVisualRoot.setGridLinesVisible(true);

		int maxBudgetVal = budgetSection.getBudgetSectionLimit();
		double amountLeft = maxBudgetVal - budgetSection.getAmountSpent();
		final ProgressBar progressBar = new ProgressBar(budgetSection.getAmountSpent() / maxBudgetVal);
		progressBar.setMaxWidth(3000);

		final Label budgetAmountLeft = new Label(amountLeft + " Left of " + maxBudgetVal);
		final Label nameLabel = new Label(budgetSection.getName());
		final ProgressIndicator progressIndicator = new ProgressIndicator(1 - (amountLeft / budgetSection.getBudgetSectionLimit()));

		setUpGrid(budgetVisualRoot, amountLeft, progressBar);

		// Add Elements to grid
		budgetVisualRoot.add(nameLabel, 0, 0);
		budgetVisualRoot.add(progressBar, 0, 1);
		budgetVisualRoot.add(progressIndicator, 1, 0);
		budgetVisualRoot.add(budgetAmountLeft, 1, 1);
	}

	private void setUpGrid(GridPane budgetVisualRoot, double amountLeft, ProgressBar progressBar)
	{
		budgetVisualRoot.setHgap(5);
		budgetVisualRoot.setVgap(5);

		// Set the gridsize
		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(85);
		ColumnConstraints col2Constraints = new ColumnConstraints();
		col2Constraints.setPercentWidth(15);

		budgetVisualRoot.getColumnConstraints().addAll(col1Constraints, col2Constraints);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(60);
		RowConstraints row2Constraints = new RowConstraints();
		row2Constraints.setPercentHeight(40);

		budgetVisualRoot.getRowConstraints().addAll(row1Constraints, row2Constraints);

		this.getStylesheets().add(SystemInfo.MAIN_STYLE_SHEET_NAME);

		// Border
		this.setStyle(  "-fx-padding: 10;" +
						"-fx-border-style: solid inside;" +
						"-fx-border-width: 2;" +
						"-fx-border-insets: 5;" +
						"-fx-border-radius: 5;" +
						"-fx-border-color: #A2FFFF;");

		if (amountLeft > 0)
		{
			progressBar.setStyle("-fx-accent: #00F625;");
		}
		else
		{
			progressBar.setStyle("-fx-accent: #FF2A00;");
		}
	}

	public BudgetSection getBudgetSection()
	{
		return budgetSection;
	}
}