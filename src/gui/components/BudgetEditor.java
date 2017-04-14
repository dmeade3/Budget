package gui.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import user.budget.BudgetSection;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class BudgetEditor extends GridPane
{
    private BudgetSection oldBudgetSection;

    public BudgetEditor(BudgetWithProgressBar budgetWithProgressBar)
    {
        oldBudgetSection = budgetWithProgressBar.getBudgetSection();

        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField(budgetWithProgressBar.getBudgetSection().getName());

        Label budgetLimitLabel = new Label("Budget Limit");
        TextField budgetLimitField = new TextField(String.valueOf(budgetWithProgressBar.getBudgetSection().getBudgetSectionLimit()));

        Button saveButton = new Button("Save");

        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        add(nameLabel, 0, 0);
        GridPane.setHalignment(nameField, HPos.LEFT);
        add(nameField, 1, 0);

        GridPane.setHalignment(budgetLimitLabel, HPos.RIGHT);
        add(budgetLimitLabel, 0, 1);
        GridPane.setHalignment(budgetLimitField, HPos.LEFT);
        add(budgetLimitField, 1, 1);


        // Save button
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        add(saveButton, 1, 6);

        // On save verify right format and replace the budget in history
        saveButton.setOnMouseClicked((MouseEvent event) ->
        {
            try
            {
                BudgetSection newBudgetSection = new BudgetSection(nameField.getText(), Integer.valueOf(budgetLimitField.getText()));

                // Get all file names under dir
                File budgetFile = new File(USERS_PATH + "\\" + CURRENT_USER + "\\budget.csv");

                Path path = Paths.get(String.valueOf(budgetFile));
                Charset charset = StandardCharsets.UTF_8;

                String content = new String(Files.readAllBytes(path), charset);

                content = content.replace(oldBudgetSection.csvToString().trim(), newBudgetSection.csvToString().trim());

                Files.write(path, content.getBytes(charset));
            }
            catch (Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formatting Error For Budget Editor");
                alert.setHeaderText("Yo my dude that format is wrong");
                alert.setContentText(e.getLocalizedMessage());

                alert.showAndWait();
            }
        });
    }
}
