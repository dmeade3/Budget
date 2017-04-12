package gui.components;

import com.opencsv.CSVReader;
import gui.RootPage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import user.budget.BudgetSection;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class BudgetTab extends Tab
{
    ListView<BudgetWithProgressBar> mainBudgetMiddleList;

    // TODO Eventually make these objects
    public BudgetTab()
    {
        super("Budget");

        ScrollPane middleScrollPane = new ScrollPane();
        middleScrollPane.setFitToHeight(true);
        middleScrollPane.setFitToWidth(true);

        mainBudgetMiddleList = new ListView<>();

        // TODO have better way to point to this file in the future
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
                    mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new BudgetSection(row[0], Integer.valueOf(row[1]), Double.valueOf(row[2]))));
                }
            }
        }
        catch (IOException e) { /* TODO logger.warn("Could not open file: " + filename); */ }

        middleScrollPane.setContent(mainBudgetMiddleList);

        setContent(middleScrollPane);
        setClosable(false);

        addListeners();
    }

    private void addListeners()
    {
        // Style the selected budget section
        mainBudgetMiddleList.setOnMouseClicked(event ->
        {
            mainBudgetMiddleList.getSelectionModel().getSelectedItem().setStyle(
                "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: #A2FFFF;" +
                "-fx-background-color: #1654FF;" +
                "-fx-text-fill: white;"); // TODO make work 
        });

        mainBudgetMiddleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            // Launch edit pane
            Stage stage = new Stage();
            Scene scene = new Scene(new BudgetEditor(newValue), 300, 200);

            stage.setScene(scene);
            stage.setTitle("Budget Editor");
            stage.show();

            stage.setOnCloseRequest(event ->
            {
                RootPage.reloadCenter();
            });
        });
    }
}
