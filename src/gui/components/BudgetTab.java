package gui.components;

import data.MainProgramDatastore;
import gui.RootPage;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class BudgetTab extends Tab
{
    ListView<BudgetWithProgressBar> mainBudgetMiddleList = new ListView<>();

    public BudgetTab()
    {
        super("Budget");

        ScrollPane middleScrollPane = new ScrollPane();
        middleScrollPane.setFitToHeight(true);
        middleScrollPane.setFitToWidth(true);

        MainProgramDatastore.getInstance().loadCurrentUser();

        mainBudgetMiddleList.setItems(FXCollections.observableArrayList(MainProgramDatastore.getInstance().getLoadedUser().getBudgets()));

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
            if (mainBudgetMiddleList.getSelectionModel().getSelectedItem() != null)
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
            }
        });

        mainBudgetMiddleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            // Launch edit pane
            Stage stage = new Stage();
            Scene scene = new Scene(new BudgetEditor(newValue), 300, 200);

            stage.setScene(scene);
            stage.setTitle("Budget Editor");
            stage.show();

            stage.setOnCloseRequest(event2 ->
            {
                RootPage.reloadCenter(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
            });
        });
    }
}
