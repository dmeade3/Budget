package gui.components;

import data.MainProgramDatastore;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class MainCenterTabPane extends TabPane
{
    public MainCenterTabPane()
    {
        getTabs().add(new BudgetTab());
        getTabs().add(new Tab("Summary"));
        getTabs().add(new TransactionTab());
        getTabs().add(new Tab("Future"));

        addListeners();
    }

    private void addListeners()
    {
        setOnMouseClicked(event ->
        {
            MainProgramDatastore.getInstance().setSelectedMainTabIndex(getSelectionModel().getSelectedIndex());
        });
    }
}
